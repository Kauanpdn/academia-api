package com.academia.academia.api.service;

import com.academia.academia.api.model.Plano;
import com.academia.academia.api.repository.PlanoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {

    private static final Logger log = LoggerFactory.getLogger(PlanoService.class);

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    // =========================
    // CADASTRAR PLANO
    // =========================
    public Plano cadastrarPlano(Plano plano) {
        log.info("Iniciando cadastro de plano | nome={}", plano.getNome());

        validarPlano(plano);

        Plano planoSalvo = planoRepository.save(plano);

        log.info("Plano cadastrado com sucesso | id={} | nome={}",
                planoSalvo.getId(), planoSalvo.getNome());

        return planoSalvo;
    }

    // =========================
    // LISTAR TODOS
    // =========================
    public List<Plano> listarPlanos() {
        log.info("Buscando lista de planos");

        List<Plano> planos = planoRepository.findAll();

        log.info("Total de planos encontrados: {}", planos.size());

        return planos;
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public Plano buscarPorId(Integer id) {
        log.info("Buscando plano por id={}", id);

        Optional<Plano> planoOpt = planoRepository.findById(id);

        if (planoOpt.isEmpty()) {
            log.warn("Plano não encontrado | id={}", id);
            throw new RuntimeException("Plano não encontrado");
        }

        log.info("Plano encontrado | id={} | nome={}",
                planoOpt.get().getId(), planoOpt.get().getNome());

        return planoOpt.get();
    }

    // =========================
    // ATUALIZAR PLANO
    // =========================
    public Plano atualizarPlano(Integer id, Plano planoAtualizado) {
        log.info("Iniciando atualização do plano | id={}", id);

        Plano planoExistente = buscarPorId(id);

        planoExistente.setNome(planoAtualizado.getNome());
        planoExistente.setValor(planoAtualizado.getValor());
        planoExistente.setDuracaoMeses(planoAtualizado.getDuracaoMeses());
        planoExistente.setDescricao(planoAtualizado.getDescricao());

        Plano planoSalvo = planoRepository.save(planoExistente);

        log.info("Plano atualizado com sucesso | id={}", planoSalvo.getId());

        return planoSalvo;
    }

    // =========================
    // REMOVER PLANO
    // =========================
    public void removerPlano(Integer id) {
        log.info("Solicitação para remover plano | id={}", id);

        if (!planoRepository.existsById(id)) {
            log.warn("Tentativa de remover plano inexistente | id={}", id);
            throw new RuntimeException("Plano não encontrado");
        }

        planoRepository.deleteById(id);

        log.info("Plano removido com sucesso | id={}", id);
    }

    // =========================
    // VALIDAÇÕES
    // =========================
    private void validarPlano(Plano plano) {
        log.debug("Validando dados do plano");

        if (plano.getNome() == null || plano.getNome().isBlank()) {
            log.error("Nome do plano é obrigatório");
            throw new IllegalArgumentException("Nome do plano é obrigatório");
        }

        if (plano.getValor() == null || plano.getValor().doubleValue() <= 0) {
            log.error("Valor do plano inválido");
            throw new IllegalArgumentException("Valor do plano deve ser maior que zero");
        }

        if (plano.getDuracaoMeses() <= 0) {
            log.error("Duração do plano inválida");
            throw new IllegalArgumentException("Duração deve ser maior que zero");
        }
    }
}
