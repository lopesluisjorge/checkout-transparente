package ecommerce.checkout.repository;

import org.springframework.data.repository.CrudRepository;

import ecommerce.checkout.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
