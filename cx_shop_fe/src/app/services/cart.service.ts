import { Injectable } from '@angular/core';
import { ProductService } from './product.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: Map<number, number> = new Map();

  constructor(private productService: ProductService) {
    const storedCart = localStorage.getItem('cart');
    if (storedCart) {
      this.cart = new Map(JSON.parse(storedCart));
    }
  }

  addToCart(productId: number, quantity: number = 1): void {
    debugger;
    if (this.cart.has(productId)) {
      // if product already in cart, increase quantity to 'quantity';
      this.cart.set(productId, this.cart.get(productId)! + quantity);
    } else {
      this.cart.set(productId, quantity);
    }
    this.saveCartToLocalStorage();
  }

  private saveCartToLocalStorage(): void {
    debugger;
    localStorage.setItem('cart', JSON.stringify(Array.from(this.cart.entries())));
  }
}
