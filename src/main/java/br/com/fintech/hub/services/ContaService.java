package br.com.fintech.hub.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fintech.hub.dtos.ContaFilterAporteTransferencia;
import br.com.fintech.hub.dtos.enums.TipoOperacao;
import br.com.fintech.hub.entities.Conta;
import br.com.fintech.hub.entities.HistoricoMovimentacao;
import br.com.fintech.hub.entities.enums.TipoConta;
import br.com.fintech.hub.exceptions.InsufficientFundsException;
import br.com.fintech.hub.exceptions.NecessaryFieldsException;
import br.com.fintech.hub.repositories.ContaRepository;
import br.com.fintech.hub.repositories.HistoricoMovimentacaoRepository;
import javassist.NotFoundException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private HistoricoMovimentacaoRepository historicoMovimentacaoRepository;
	
	public List<Conta> buscarTodos() {
		return contaRepository.findAll();
	}
	
	public Conta buscarConta(Long id) throws NotFoundException {
		Conta conta = contaRepository.findOne(id);
		if(conta != null)
			return conta;
		throw new NotFoundException("Conta com id: " + id + " não encontrada!");
	}

	@Transactional
	public void delete(Long id) throws NotFoundException {
		Conta conta = buscarConta(id);
		contaRepository.delete(conta);
	}

	@Transactional
	public Conta atualizar(Conta conta, Long id) throws Exception {
		validate(conta);
		Conta contaAtualizada = buscarConta(id);
		if(contaAtualizada != null) {
			contaAtualizada.setDataCriacao(conta.getDataCriacao());
			contaAtualizada.setPessoa(conta.getPessoa());
			contaAtualizada.setStatusConta(conta.getStatusConta());
			contaAtualizada.setTipoConta(conta.getTipoConta());
			
			contaAtualizada = contaRepository.save(contaAtualizada);
		}
		return contaAtualizada;
	}

	@Transactional
	public Conta criar(Conta conta) throws Exception {
		validate(conta);
		Conta contaAtualizada = contaRepository.save(conta);
		return contaAtualizada;
	}

	@Transactional
	public Conta aporteTransferencia(ContaFilterAporteTransferencia conta) throws Exception {
		Conta contaResultado = null;
		
		if(conta.getTipoOperacao().equals(TipoOperacao.APORTE))
			contaResultado = aporte(conta.getContaDestino(), conta.getValor(), conta.getJustificativa());
		if(conta.getTipoOperacao() != null && conta.getTipoOperacao().equals(TipoOperacao.TRANSFERENCIA))
			contaResultado = transferir(conta.getContaOrigem(), conta.getContaDestino(), conta.getValor());
		
		return contaResultado;
	}
	private HistoricoMovimentacao generate(Conta contaOrigem, Conta contaDestino, BigDecimal valor, String descricao, TipoOperacao tipoOperacao) {
		HistoricoMovimentacao historicoMovimentacao = new HistoricoMovimentacao();
		historicoMovimentacao.setDataMovimentacao(LocalDateTime.now());
		historicoMovimentacao.setContaDestino(contaDestino);
		historicoMovimentacao.setContaOrigem(contaOrigem);
		historicoMovimentacao.setValorMovimentado(valor);
		historicoMovimentacao.setDescricao(descricao);
		historicoMovimentacao.setTipoOperacao(tipoOperacao);
		historicoMovimentacaoRepository.save(historicoMovimentacao);
		return historicoMovimentacao;
	}
	
	@Transactional
	private Conta transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) throws Exception {
		if(contaOrigem != null && contaDestino != null) {
			Conta contaO = buscarConta(contaOrigem.getId());
			Conta contaD = buscarConta(contaDestino.getId());
			if(contaO.getSaldo().compareTo(valor) < 0) {
				throw new InsufficientFundsException("Conta de Origem não possui Saldo suficiente para transferência Saldo Conta("+contaO.getSaldo()+") e valor da transferência ("+valor+")");
			}
			contaD.setSaldo(contaD.getSaldo().add(valor));
			contaO.setSaldo(contaO.getSaldo().subtract(valor));
			contaO = contaRepository.save(contaO);
			contaD = contaRepository.save(contaD);
			
			generate(contaO, contaD, valor, "Transferência", TipoOperacao.TRANSFERENCIA);
			
			return contaD;
		}
		throw new Exception("Contas devem ser informadas para transferência");
	}
	
	@Transactional
	private Conta aporte(Conta conta, BigDecimal aporte, String origemAporte) throws Exception  {
		if(conta != null) {
			Conta contaD = buscarConta(conta.getId());
			contaD.setSaldo(contaD.getSaldo().add(aporte));
			contaD = contaRepository.save(contaD);

			generate(null, contaD, aporte, "Aporte: " + origemAporte, TipoOperacao.APORTE);
			
			return contaD;
		}
		throw new Exception("Conta deve ser informadas para aporte");
	}
	
	private void validate(Conta conta) throws Exception {
		if(conta != null) {
			List<String> necessaryFields = new ArrayList<>();
			
			if(conta.getPessoa() == null || conta.getPessoa().getId() == null)
				necessaryFields.add("PESSOA");
			if(conta.getDataCriacao() == null)
				necessaryFields.add("DATACRIACAO");
			if(conta.getTipoConta().equals(TipoConta.FILIAL) && conta.getContaPai() == null)
				necessaryFields.add("CONTAMATRIZ");
			
			if(!necessaryFields.isEmpty())
				throw new NecessaryFieldsException("Campos obrigatórios não informados!");
			return;
		}
		throw new Exception("Conta não deve ser nula!");
	}
}
