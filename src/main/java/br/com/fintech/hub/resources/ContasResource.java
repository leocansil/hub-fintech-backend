package br.com.fintech.hub.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fintech.hub.dtos.ContaFilterAporteTransferencia;
import br.com.fintech.hub.dtos.enums.TipoOperacao;
import br.com.fintech.hub.entities.Conta;
import br.com.fintech.hub.services.ContaService;

@RestController
@RequestMapping("/contas")
public class ContasResource {

	@Autowired
	private ContaService contaService;
	
	@GetMapping
	public ResponseEntity<List<Conta>> buscarTodos() throws Exception {
		return ResponseEntity.ok().body(contaService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Conta> buscarConta(@PathVariable("id") Long id) throws Exception {
		return ResponseEntity.ok().body(contaService.buscarConta(id));
	}
	
	@PostMapping
	public ResponseEntity<Conta> criar(@Valid @RequestBody Conta conta) throws Exception {
		Conta contaSalva = contaService.criar(conta);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(contaSalva.getId()).toUri();
		
		return ResponseEntity.created(uri).body(contaSalva);
	}

	@PutMapping
	public ResponseEntity<Conta> atualizarConta(@Valid @RequestBody Conta conta, @PathVariable("id") Long id) throws Exception {
		Conta contaSalva = contaService.atualizar(conta, id);
		return ResponseEntity.ok(contaSalva);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws Exception {
		contaService.delete(id);
	}
	
	@PostMapping("/transferencia")
	public ResponseEntity<Conta> transferencia(@Valid @RequestBody ContaFilterAporteTransferencia conta) throws Exception {
		conta.setTipoOperacao(TipoOperacao.TRANSFERENCIA);
		Conta contaSalva = contaService.aporteTransferencia(conta);

		return ResponseEntity.ok().body(contaSalva);
	}

	@PostMapping("/aporte")
	public ResponseEntity<Conta> aporte(@Valid @RequestBody ContaFilterAporteTransferencia conta) throws Exception {
		conta.setTipoOperacao(TipoOperacao.APORTE);
		Conta contaSalva = contaService.aporteTransferencia(conta);

		return ResponseEntity.ok().body(contaSalva);
	}

}
