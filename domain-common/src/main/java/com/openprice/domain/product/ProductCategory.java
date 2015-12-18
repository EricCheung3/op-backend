package com.openprice.domain.product;

public enum ProductCategory {
    apparel("Apparel"),
    babyfood("Baby Food"),
    babyitems("Baby Items"),
    bakery("Bakery"),
    baking("Baking"),
    beverages("Beverages"),
    canned("Canned & Packaged"),
    cereal("Breakfast & Cereal"),
    cleaningsupplies("Cleaning Supplies"),
    dairy("Dairy"),
    deli("Deli"),
    entertainment("Entertainment"),
    floral("Floral"),
    frozen("Frozen"),
    fruit("Fruits"),
    grains("Beans, Grains & Rice"),
    health("Health & Beauty"),
    home("Home"),
    international("International & Ethnic"),
    jams("Jams & Preserves"),
    meat("Meat"),
    nuts("Nuts & Dried Fruit"),
    oil("Oil & Vinegar"),
    papergoods("Paper Goods"),
    pasta("Pasta & Noodles"),
    personalitems("Personal Care"),
    petfood("Pet Food"),
    pharmacy("Pharmacy"),
    sauces("Condiments & Sauces"),
    seafood("Seafood"),
    snacks("Snacks"),
    soup("Soups & Broth"),
    spices("Spices"),
    uncategorized("Uncategorized"),
    vegetables("Vegetables"),
    wine("Wine & Spirits");

    private String label;

    private ProductCategory(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
