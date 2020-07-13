package ecommerce.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.checkout.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}
