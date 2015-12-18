-- add product_category to receipt_item table
ALTER TABLE receipt_item
ADD product_category VARCHAR(50) NOT NULL;

-- add product_category to shopping_item table
ALTER TABLE shopping_item
ADD product_category VARCHAR(50) NOT NULL;

-- update catalog table
-- change table name ot catalog_product
-- change column 'code' to catalog_code
-- add product_category
ALTER TABLE catalog
CHANGE COLUMN code catalog_code VARCHAR(255) NOT NULL;

ALTER TABLE catalog
ADD UNIQUE (chain_id, catalog_code);

ALTER TABLE catalog
ADD product_category VARCHAR(50) NOT NULL;

ALTER TABLE catalog RENAME catalog_product;

