import { Component, OnInit } from '@angular/core';
import { OrderResponse } from '../../responses/order/order.resposne';
import { OrderService } from '../../services/order.service';
import { OrderDetail } from '../../models/order.detail';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order.detail.component.html',
  styleUrls: ['./order.detail.component.scss']
})
export class OrderDetailComponent implements OnInit {
  orderResponse: OrderResponse = {
    id: 0,
    user_id: 0,
    full_name: '',
    phone_number: '',
    email: '',
    address: '',
    note: '',
    order_date: new Date(),
    status: '',
    total_money: 0,
    shipping_method: '',
    shipping_address: '',
    shipping_date: new Date(),
    payment_method: '',
    order_details: []
  }

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(): void {
    debugger;
    const orderId = 10 // fake id
    this.orderService.getOrderById(orderId).subscribe({
      next: (response: any) => {
        debugger;
        this.orderResponse = {
          ...response,
          order_date: new Date(
            response.order_date[0],
            response.order_date[1] - 1,
            response.order_date[2],
          ),
          shipping_date: new Date(
            response.shipping_date[0],
            response.shipping_date[1] - 1,
            response.shipping_date[2]
          ),
          order_details: response.order_details.map((order_detail: OrderDetail) => ({
            ...order_detail,
            product: {
              ...order_detail.product,
              thumbnail: `${environment.apiBaseUrl}/products/images/${order_detail.product.thumbnail}`
            }
          })),
        };
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching detail: ', error);
      }
    });
  }
}
