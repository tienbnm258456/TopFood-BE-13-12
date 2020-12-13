package com.demo.shop.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.shop.constant.CategoryConstant;
import com.demo.shop.constant.StatusConstant;
import com.demo.shop.entity.Category;
import com.demo.shop.entity.Product;
import com.demo.shop.entity.Supplier;
import com.demo.shop.repository.CategoryRepository;
import com.demo.shop.repository.ProductRepository;
import com.demo.shop.repository.SupplierRepository;
import com.demo.shop.request.ProductRequest;
import com.demo.shop.response.ProductResponse;
import com.demo.shop.response.TotalProductResponse;
import com.demo.shop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	SupplierRepository supplierRepository;

	@Value("E:/full_du_an_2/Top-Food-FE-23-11-2020")

	private String urlToFrontEnd;

	@Override
	public List<ProductResponse> getAll() {
		List<Product> products = productRepository.findByStatus(StatusConstant.STATUS_ACTIVE);
		List<ProductResponse> productResponseList = new ArrayList<>();
		for (Product product : products) {
			ProductResponse productDTORespon = new ProductResponse();
			productDTORespon.setId(product.getId());
			productDTORespon.setProductName(product.getProductName());
			productDTORespon.setCategory(product.getCategory());
			productDTORespon.setSupplier(product.getSupplier());
			productDTORespon.setPrice(product.getPrice());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if(product.getCreatedDate() != null) {
				productDTORespon.setCreatedDate(product.getCreatedDate().format(formatter));
			}
			productDTORespon.setDescription(product.getDescription());
			productDTORespon.setPriceSale(product.getPriceSale());
			productDTORespon.setImage(product.getImage());
			productDTORespon.setStatus(product.getStatus());

			productResponseList.add(productDTORespon);
		}
		return productResponseList;
	}

	@Override
	public Product add(ProductRequest productRequest) {
		Product product = convertProductRequestToProduct(productRequest);
		return productRepository.save(product);
	}

	@Override
	public ProductResponse getById(Integer id) {
		ProductResponse productResponse = new ProductResponse();
		Optional<Product> product = productRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE);
		if (product != null) {
			productResponse.setId(product.get().getId());
			productResponse.setPrice(product.get().getPrice());
			productResponse.setCategory(product.get().getCategory());
			productResponse.setSupplier(product.get().getSupplier());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			productResponse.setCreatedDate(product.get().getCreatedDate().format(formatter));
			productResponse.setDescription(product.get().getDescription());
			productResponse.setPriceSale(product.get().getPriceSale());
			productResponse.setProductName(product.get().getProductName());
			productResponse.setImage(product.get().getImage());
		}
		return productResponse;
	}

	@Override
	public Product update(Integer id, ProductRequest productRequest) throws IOException {
		Product product = productRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("id with not found = " + id));
		FileOutputStream fos;
		product.setCategoryId(productRequest.getCategoryId());
		product.setSupplierId(productRequest.getSupplierId());
		product.setDescription(productRequest.getDescription());
		product.setPrice(productRequest.getPrice());
		product.setPriceSale(productRequest.getPriceSale());
		product.setProductName(productRequest.getProductName());
		product.setUpdatedDate(LocalDateTime.now());

		if (productRequest.getImageBase64() != null && !productRequest.getImageBase64().equals("")) {
			String[] byteImage = productRequest.getImageBase64().split(",");
			byte[] imageByte = Base64.getDecoder().decode(byteImage[1]);
			String imageName = System.currentTimeMillis() + ".png";
			File file = new File(urlToFrontEnd + "/src/assets/images/product");
			String pathFileImages = file.getAbsolutePath() + "/"
					+ imageName;
			fos = new FileOutputStream(pathFileImages);
			fos.write(imageByte);
			product.setImage("/assets/images/product/" + imageName);
		}
		return productRepository.save(product);
	}

	@Override
	public Product delete(Integer id) {
		Product product = productRepository.findByIdAndStatus(id, StatusConstant.STATUS_ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException("id with not found = " + id));
		product.setStatus(StatusConstant.STATUS_INACTIVE);
		return productRepository.save(product);
	}

	@Override
	public List<ProductResponse> getProductByCateGoryId(Integer categoryId) {
		List<Product> productList = productRepository.findProductsByCategoryId(categoryId);
		List<ProductResponse> productResponseList = new ArrayList<>();
		for (Product product : productList) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setId(product.getId());
			productResponse.setProductName(product.getProductName());
			productResponse.setPrice(product.getPrice());
			productResponse.setDescription(product.getDescription());
			productResponse.setStatus(product.getStatus());
			productResponse.setPriceSale(product.getPriceSale());
			productResponse.setCategory(product.getCategory());
			productResponse.setSupplier(product.getSupplier());
			productResponse.setImage(product.getImage());
			productResponseList.add(productResponse);
		}
		return productResponseList;
	}

	@Override
	public List<ProductResponse> getProductByCateGoryIdTop4(Integer categoryId) {
		List<Product> productList = productRepository.findProductsByCategoryIdTop4(categoryId);
		List<ProductResponse> productResponseList = new ArrayList<>();
		for (Product product : productList) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setId(product.getId());
			productResponse.setProductName(product.getProductName());
			productResponse.setPrice(product.getPrice());
			productResponse.setDescription(product.getDescription());
			productResponse.setStatus(product.getStatus());
			productResponse.setPriceSale(product.getPriceSale());
			productResponse.setCategory(product.getCategory());
			productResponse.setSupplier(product.getSupplier());
			productResponse.setImage(product.getImage());
			productResponseList.add(productResponse);
		}
		return productResponseList;
	}

	@Override
	public List<ProductResponse> getTop4Product() {
		List<Product> listProduct = productRepository.findTop4Product(StatusConstant.STATUS_ACTIVE);
		List<ProductResponse> productResponseList = new ArrayList<>();
		for (Product product : listProduct) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setId(product.getId());
			productResponse.setId(product.getId());
			productResponse.setProductName(product.getProductName());
			productResponse.setCategory(product.getCategory());
			productResponse.setSupplier(product.getSupplier());
			productResponse.setPrice(product.getPrice());
			productResponse.setDescription(product.getDescription());
			productResponse.setPriceSale(product.getPriceSale());
			productResponse.setStatus(product.getStatus());
			productResponse.setImage(product.getImage());
			productResponseList.add(productResponse);
		}
		return productResponseList;
	}

	@Override
	public List<ProductResponse> getTop8Fruit() {
		List<Product> listProduct = productRepository.findTop8FruitProduct(StatusConstant.STATUS_ACTIVE, CategoryConstant.Fruit);
		List<ProductResponse> productResponses = new ArrayList<>();
		for (Product product : listProduct) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setId(product.getId());
			productResponse.setProductName(product.getProductName());
			productResponse.setCategory(product.getCategory());
			productResponse.setSupplier(product.getSupplier());
			productResponse.setPrice(product.getPrice());
			productResponse.setDescription(product.getDescription());
			productResponse.setPriceSale(product.getPriceSale());
			productResponse.setStatus(product.getStatus());
			productResponse.setImage(product.getImage());
			productResponses.add(productResponse);
		}
		return productResponses;
	}


	@Override
	public List<ProductResponse> searchByProductName(String productName) {
		List<Product> products = productRepository.findAllByProductName(productName);
		List<ProductResponse> productResponseList = new ArrayList<>();
		for (Product product : products) {
			ProductResponse productDTORespon = new ProductResponse();
			productDTORespon.setId(product.getId());
			productDTORespon.setProductName(product.getProductName());
			productDTORespon.setCategory(product.getCategory());
			productDTORespon.setSupplier(product.getSupplier());
			productDTORespon.setPrice(product.getPrice());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if(product.getCreatedDate() != null) {
				productDTORespon.setCreatedDate(product.getCreatedDate().format(formatter));
			}
			productDTORespon.setDescription(product.getDescription());
			productDTORespon.setPriceSale(product.getPriceSale());
			productDTORespon.setStatus(product.getStatus());
			productDTORespon.setImage(product.getImage());
			productResponseList.add(productDTORespon);
		}
		return productResponseList;
	}

	@Override
	public ProductResponse create(ProductRequest request) throws IOException {
		FileOutputStream fos;
		Product product = convertProductRequestToProduct(request);
		if (request.getImageBase64() != null && !request.getImageBase64().equals("")) {
			String[] byteImage = request.getImageBase64().split(",");
			byte[] imageByte = Base64.getDecoder().decode(byteImage[1]);
			String imageName = System.currentTimeMillis() + ".png";
			File file = new File(urlToFrontEnd + "/src/assets/images/product");
			String pathFileImages = file.getAbsolutePath() + "/"
					+ imageName;
			fos = new FileOutputStream(pathFileImages);
			fos.write(imageByte);
			product.setImage("/assets/images/product/" + imageName);
		}
		return convertProductToProductResponse(productRepository.save(product));
	}


	@Override
	public TotalProductResponse countProduct() {
		TotalProductResponse countProductResponse = new TotalProductResponse();
		Integer count = productRepository.CountProduct(StatusConstant.STATUS_ACTIVE);
		countProductResponse.setTotalProduct(count);
		return countProductResponse;
	}

	private Product convertProductRequestToProduct(ProductRequest request) {
		Product product = new Product();
		product.setCategoryId(request.getCategoryId());
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));
		product.setCategory(category);
		product.setSupplierId(request.getSupplierId());
		Supplier supplier = supplierRepository.findById(product.getSupplierId())
				.orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
		product.setSupplier(supplier);
		product.setCreatedDate(LocalDateTime.now());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setPriceSale(request.getPriceSale());
		product.setProductName(request.getProductName());
		product.setImage(product.getImage());
		product.setStatus(StatusConstant.STATUS_ACTIVE);
		return product;
	}

	private ProductResponse convertProductToProductResponse(Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(product.getId());
		productResponse.setProductName(product.getProductName());
		productResponse.setPrice(product.getPrice());
		productResponse.setDescription(product.getDescription());
		productResponse.setStatus(product.getStatus());
		productResponse.setPriceSale(product.getPriceSale());
		productResponse.setCreatedDate(product.getCreatedDate().toString());
		productResponse.setCategory(product.getCategory());
		productResponse.setSupplier(product.getSupplier());
		productResponse.setImage(product.getImage());
		return productResponse;
	}

}
