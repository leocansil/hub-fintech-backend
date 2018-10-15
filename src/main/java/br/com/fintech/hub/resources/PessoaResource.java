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

import br.com.fintech.hub.entities.Pessoa;
import br.com.fintech.hub.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> listarTodos() throws Exception{
		return ResponseEntity.ok(pessoaService.listarTodos()); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(pessoaService.buscarPessoa(id));
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa) throws Exception {
		
		Pessoa pessoaSalva = pessoaService.criar(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(pessoaSalva.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizarPessoa(@Valid @RequestBody Pessoa pessoa, @PathVariable("id") Long id) throws Exception {
		Pessoa pessoaAtualizada = pessoaService.atualizar(pessoa, id);
		return ResponseEntity.ok(pessoaAtualizada);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws Exception {
		pessoaService.delete(id);
	}
}
