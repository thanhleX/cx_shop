import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderDetailAdminComponent } from './order-detail/order.detail.admin.component';
import { AdminComponent } from './admin.component';
import { OrderAdminComponent } from './order/order.admin.component';
import { CategoryAdminComponent } from './category/category.admin.component';
import { ProductAdminComponent } from './product/product.admin.component';
import { AdminRoutingModule } from './admin-routing.module';



@NgModule({
  declarations: [
    AdminComponent,
    OrderAdminComponent,
    CategoryAdminComponent,
    ProductAdminComponent,
    OrderDetailAdminComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
  ]
})
export class AdminModule { }
