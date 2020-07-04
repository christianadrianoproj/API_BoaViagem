package com.christian.api.BoaViagem.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.christian.api.BoaViagem.domain.Gasto;
import com.christian.api.BoaViagem.domain.Viagem;
import com.christian.api.BoaViagem.repository.GastoRepository;
import com.christian.api.BoaViagem.repository.ViagemRepository;

/**
 *
 * @author Christian
 */

@RestController
@RequestMapping("/viagem")
@CrossOrigin(origins = "*")
public class ViagemController {

	@Autowired
	private ViagemRepository repository;

	@Autowired
	private GastoRepository repositoryGasto;

	@GetMapping
	public List<Viagem> findAll() {
		List<Viagem> lista = repository.findAll(Sort.by("dataChegada"));
		for (Viagem r : lista) {
			List<Gasto> gastos = r.getGastos();
			gastos.sort(Comparator.comparing(Gasto::getTipo));
			r.setGastos(gastos);
		}
		return lista;
	}

	private Date StringToDate(String dateStr) {
	    Date date = null;
	    try {
	        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        date = (java.util.Date)formatter.parse(dateStr);
	    } catch (ParseException e) {      
	    	System.out.println("Erro na conversao: " + e.getMessage());
	    	date = null;
	    }
	    return date;
	}

	@GetMapping("/ViagensDoUsuario/{idusuario}")
	public List<Viagem> ViagensDoUsuario(@PathVariable("idusuario") Integer idusuario) {
		List<Viagem> lista = repository.findAll(Sort.by("dataPartida"));
		ArrayList<Viagem> retorno = new ArrayList<Viagem>();
		for (Viagem r : lista) {
			if (r.getUsuario().getIdUsuario() == idusuario) {
				List<Gasto> gastos = r.getGastos();
				gastos.sort(Comparator.comparing(Gasto::getData));
				r.setGastos(gastos);
				retorno.add(r);
			}
		}
		return retorno;
	}

/*	@PostMapping("/ValidaPeriodoViagem/{idviagem}")
	public Viagem ValidaDataViagem(@PathVariable("idviagem") Integer idviagem, @RequestBody String data) {
		Viagem retorno = new Viagem();
		System.out.println("Data recebida: " + data);
		Date dataFiltro = StringToDate(data);
		System.out.println(dataFiltro);
		Viagem r = repository.findById(idviagem).get();
		if ((r.getDataChegada() != null) && (dataFiltro != null)) {
			Date dataChegada = StringToDate(r.getDataChegada());
			if (dataChegada != null) {
				if ((dataChegada.after(dataFiltro)) || (dataChegada.equals(dataFiltro))) {
					retorno = r;
					if (r.getDataPartida() != null) {
						Date dataPartida = StringToDate(r.getDataPartida());
						if (dataPartida != null) {
							if ((dataPartida.before(dataFiltro)) || (dataPartida.equals(dataFiltro))) {
								retorno = r;
							} else {
								retorno = new Viagem();
							}
						}
					}
				}
			}
		}

		return retorno;
	}*/
	
	@PostMapping("/ValidaPeriodoViagem/{idviagem}")
	public Viagem ValidaDataViagem(@PathVariable("idviagem") Integer idviagem, @RequestBody String data) {
		Viagem retorno = new Viagem();
		System.out.println("Data recebida: " + data);
		//Date dataFiltro = data;
		Date dataFiltro = StringToDate(data);
		Viagem r = repository.findById(idviagem).get();
		if ((r.getDataChegada() != null) && (dataFiltro != null)) {
			Date dataChegada = r.getDataChegada();
			if (dataChegada != null) {
				System.out.println("	dataChegada: " + dataChegada);
				System.out.println("	dataFiltro: " + dataFiltro);
				if ((dataChegada.after(dataFiltro)) || (dataChegada.equals(dataFiltro))) {
					retorno = r;
					if (r.getDataPartida() != null) {
						Date dataPartida = r.getDataPartida();
						if (dataPartida != null) {
							System.out.println("	dataPartida: " + dataPartida);
							System.out.println("	dataFiltro: " + dataFiltro);
							if ((dataPartida.before(dataFiltro)) || (dataPartida.equals(dataFiltro))) {
								retorno = r;
							} else {
								retorno = new Viagem();
							}
						}
					}
				}
			}
		}

		return retorno;
	}

	@PostMapping("/salvaViagem")
	public Viagem salvar(@RequestBody Viagem v) {
		Viagem var = repository.save(v);
		v.setIdViagem(var.getIdViagem());
		if (v.getGastos() != null) {
			for (Gasto i : v.getGastos()) {
				i.setViagem(var);
				;
				repositoryGasto.save(i);
			}
		}
		return repository.findById(var.getIdViagem()).get();
	}

	@PostMapping("/adicionaGasto/{idviagem}")
	public ResponseEntity<?> adicionaGasto(@PathVariable("idviagem") Integer idviagem, @RequestBody Gasto gasto) {
		Viagem viagem = repository.findById(idviagem).get();
		gasto.setViagem(viagem);
		repositoryGasto.save(gasto);
		Optional<Viagem> r = repository.findById(idviagem);
		return ResponseEntity.ok(r.get());
	}

	@PostMapping("/deletaGasto/{idviagem}")
	public Viagem deletaGasto(@PathVariable("idviagem") Integer idviagem, @RequestBody Gasto gasto) {
		repositoryGasto.deleteById(gasto.getIdGasto());
		return repository.findById(idviagem).get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable("id") Integer id) {
		Optional<Viagem> viagem = repository.findById(id);
		if (viagem.isPresent()) {
			return ResponseEntity.ok(viagem.get());
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		Viagem viagem = repository.findById(id).get();
		for (Gasto i : viagem.getGastos()) {
			repositoryGasto.deleteById(i.getIdGasto());
		}
		viagem = repository.findById(id).get();
		repository.deleteById(id);
	}
}