SELECT setval('accounts_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM accounts) )  ; 
 
SELECT setval('attachments_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM attachments) )  ; 
 
SELECT setval('carriers_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM carriers) )  ; 
 
SELECT setval('categories_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM categories) )  ; 
 
SELECT setval('client_addresses_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM client_addresses) )  ; 
 
SELECT setval('clients_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM clients) )  ; 
 
SELECT setval('currencies_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM currencies) )  ; 
 
SELECT setval('hashtag_products_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM hashtag_products) )  ; 
 
SELECT setval('hashtags_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM hashtags) )  ; 
 
SELECT setval('invoices_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM invoices) )  ; 
 
SELECT setval('merchants_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM merchants) )  ; 
 
SELECT setval('navigations_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM navigations) )  ; 
 
SELECT setval('orders_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM orders) )  ; 
 
SELECT setval('product_categories_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM product_categories) )  ; 
 
SELECT setval('product_images_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM product_images) )  ; 
 
SELECT setval('product_specifications_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM product_specifications) )  ; 
 
SELECT setval('products_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM products) )  ; 
 
SELECT setval('system_informations_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM system_informations) )  ; 
 
SELECT setval('usa_order_items_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM usa_order_items) )  ; 
 
SELECT setval('usa_orders_id_seq', (SELECT COALESCE(MAX(id),0)+1 FROM usa_orders) )  ; 
 
