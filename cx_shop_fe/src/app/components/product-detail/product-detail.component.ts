import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { CategoryService } from '../../services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductImage } from '../../models/product.image';
import { environment } from '../../environments/environment';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'
})
export class ProductDetailComponent implements OnInit {
  product?: Product;
  productId: number = 0;
  currentImageIndex: number = 0;
  quantity: number = 1;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router,
    private activedRoute: ActivatedRoute,
    private cartService: CartService) { }

  ngOnInit() {
    const idParam = this.activedRoute.snapshot.paramMap.get('id');
    debugger;
    // const idParam = 9; // fake 1 temp value
    if (idParam !== null) {
      this.productId = +idParam;
    }

    if (!isNaN(this.productId)) {
      this.productService.getDetailProduct(this.productId).subscribe({
        next: (response: any) => {
          debugger;
          if (response.product_images && response.product_images.length > 0) {
            response.product_images.forEach((product_image: ProductImage) => {
              product_image.image_url = `${environment.apiBaseUrl}/products/images/${product_image.image_url}`;
            });
          }
          debugger;
          this.product = response;
          this.showImage(0);
        },
      })
    }
  }

  showImage(index: number) {
    debugger;
    if (this.product && this.product.product_images && this.product.product_images.length > 0) {
      if (index < 0) {
        index = 0;
      } else if (index >= this.product.product_images.length) {
        index = this.product.product_images.length - 1;
      }
      this.currentImageIndex = index;
    }
  }

  thumbnailClick(index: number) {
    this.currentImageIndex = index;
    debugger;
  }

  previousImage(): void {
    debugger;
    this.showImage(this.currentImageIndex - 1);
  }

  nextImage(): void {
    debugger;
    this.showImage(this.currentImageIndex + 1);
  }

  addToCart(): void {
    debugger;
    if (this.product) {
      this.cartService.addToCart(this.product.id, this.quantity);
      alert('them vao gio hang thanh cong');
    } else {
      console.log("cannot add product to cart because product id is null");
    }
  }

  increaseQuantity(): void {
    this.quantity++;
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  buyNow(): void {
    if (this.product) {
      this.cartService.addToCart(this.product.id, this.quantity);
      this.router.navigate(['/orders']);
    } else {
      console.log("cannot buy product because product id is null");
    }
  }
}
