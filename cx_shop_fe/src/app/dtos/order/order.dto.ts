export class OrderDto {
    user_id: number;
    full_name: string;
    email: string;
    phone_number: string;    
    address: string;    
    note: string;
    total_money: number;
    payment_method: string;    
    shipping_method: string;
    coupon_code: string;    
    cart_items: { product_id: number; quantity: number }[];

    constructor(data: any) {
        this.user_id = data.user_id;
        this.full_name = data.full_name;
        this.email = data.email;
        this.phone_number = data.phone_number;
        this.address = data.address;
        this.note = data.note;
        this.total_money = data.total_money;
        this.shipping_method = data.shipping_method;
        this.payment_method = data.payment_method;
        this.coupon_code = data.coupon_code;
        this.cart_items = data.cart_items;
    }
}