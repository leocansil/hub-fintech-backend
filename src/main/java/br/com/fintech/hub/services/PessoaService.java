package br.com.fintech.hub.services;

import java.util.ArrayList;
import java.util.List;

import javax.print.CancelablePrintJob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fintech.hub.entities.Pessoa;
import br.com.fintech.hub.entities.enums.TipoPessoa;
import br.com.fintech.hub.repositories.PessoaRepository;

@Service
public class PessoaService {

	private final int [] PESO_CPF  = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	private final int [] PESO_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	private final String CPF  = "CPF";
	private final String CNPJ = "CNPJ";
	
	private int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice-- ) {
			digito = Integer.parseInt(str.substring(indice,  indice + 1));
			soma =+ digito * peso[peso.length-str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}
	
	private boolean isValidCPF(String cpf) {
		if(cpf == null || cpf.length() != 11)
			return false;
		Integer digito1 = calcularDigito(cpf.substring(0, 9), PESO_CPF);
		Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, PESO_CPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}
	
	private boolean isValidCNPJ(String cnpj) {
		if(cnpj == null || cnpj.length() != 14)
			return false;
		Integer digito1 = calcularDigito(cnpj.substring(0, 12), PESO_CNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, PESO_CNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}
	
	private void validateDocumento(String documento, String tipoDocumento) throws Exception {
		documento = documento.trim().replace(".", "").replace("-","").replaceAll("/", "");
		if(tipoDocumento.equals(CPF)){
			if(!isValidCPF(documento)){
				throw new Exception("CPF não válido!");
			}
		}
		if(tipoDocumento.equals(CNPJ)){
			if(!isValidCNPJ(documento)) {
				throw new Exception("CNPJ não válido!");
			}
		}
	}
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	private void validate(Pessoa pessoa) throws Exception{
		if(pessoa != null) {
			List<String> camposObrigatorios = new ArrayList<>();
			if(pessoa.getNomeOuRazaoSocial() == null || pessoa.getNomeOuRazaoSocial().isEmpty()) {
				camposObrigatorios.add("NOME");
			}
			if(pessoa.getCpfCnpj() == null || pessoa.getCpfCnpj().isEmpty()) {
				camposObrigatorios.add("CPF/CNPJ");
			}
			if(pessoa.getTipoPessoa().equals(TipoPessoa.FISICA)) {
				if(pessoa.getDataNascimento() == null)
					camposObrigatorios.add("DATA");
				validateDocumento(pessoa.getCpfCnpj(), CPF);
			}
			if(pessoa.getTipoPessoa().equals(TipoPessoa.JURIDICA)) {
				validateDocumento(pessoa.getCpfCnpj(), CNPJ);
			}
			
			if(!camposObrigatorios.isEmpty()) {
				throw new Exception("Campos obrigatórios não foram respeitados!");
			}
		}
		throw new Exception("Pessoa não deve ser nula");
	}
	

	public List<Pessoa> listarTodos() {
		return pessoaRepository.findAll();
	}

	public Pessoa criar(Pessoa pessoa) throws Exception {
		validate(pessoa);
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		return pessoaSalva;
	}

	public Pessoa buscarPessoa(Long id) throws Exception {
		Pessoa pessoa = pessoaRepository.findOne(id);
		if(pessoa != null)
			return pessoa;
		throw new Exception("Pessoa não encontrada");
	}

	public Pessoa atualizar(Pessoa pessoa, Long id) throws Exception {
		validate(pessoa);
		Pessoa pessoaSalva = pessoaRepository.findByCpfCnpj(pessoa.getCpfCnpj());
		if(pessoaSalva != null && !pessoaSalva.getId().equals(id))
			throw new Exception("Não é possível atualizar pessoa, geraria duplicidade de dados para Documento!");
		pessoaSalva = buscarPessoa(id);
		pessoaSalva.setNomeFantasia(pessoa.getNomeFantasia());
		pessoaSalva.setNomeOuRazaoSocial(pessoa.getNomeOuRazaoSocial());
		pessoaSalva.setCpfCnpj(pessoa.getCpfCnpj());
		pessoaSalva.setDataNascimento(pessoa.getDataNascimento());
		pessoaSalva.setTipoPessoa(pessoa.getTipoPessoa());
		
		return pessoaRepository.save(pessoaSalva);
	}

	public void delete(Long id) throws Exception {
		Pessoa pessoa = this.buscarPessoa(id);
		pessoaRepository.delete(pessoa);
	}
}
