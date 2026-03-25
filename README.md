# Android_KT02 - Room + SharedPreferences Demo

Ung dung demo ban hang voi cac chuc nang:

- Dang nhap
- Xem danh sach products
- Xem danh sach categories
- Xem chi tiet product
- Tao hoa don (yeu cau dang nhap)
- Thanh toan va hien thi hoa don da paid

## Cong nghe

- Java + Android SDK
- Room Database (5 bang: users, categories, products, orders, order_details)
- SharedPreferences cho session dang nhap

## Du lieu mau

Seed tu dong khi mo app lan dau:

- User:
  - `admin / 123456`
  - `alice / 123456`
  - `bob / 123456`
- Categories va products mau

## Luong chinh

1. Mo app tai `MainActivity`
2. Dang nhap (neu chua dang nhap)
3. Xem products/categories
4. Mo chi tiet product va them vao hoa don
5. Xem gio hang (`CartActivity`)
6. Checkout -> cap nhat order sang `PAID`
7. Hien thi invoice (`InvoiceActivity`)

## Build nhanh

```powershell
Set-Location "D:\Coding\Android_KT02"
.\gradlew.bat :app:assembleDebug
```

