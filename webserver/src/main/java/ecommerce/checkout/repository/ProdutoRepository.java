package ecommerce.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ecommerce.checkout.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, PagingAndSortingRepository<Produto, Long> {

}
