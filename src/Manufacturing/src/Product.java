package src.Manufacturing.src;

import src.Design.src.FinalDesign;
import src.Manufacturing.src.interfaces.ProductInterface;

import java.util.HashMap;
import java.util.Map;

public class Product implements ProductInterface {

    FinalDesign design;
    String name;
    String description;
    String quantity;
    String category;
    String price;

    public Product(String name) {
        this.name = name;

    }

    @Override
    public void setName(String name) {

        if (this.design == null) {
            this.design = new FinalDesign(name);
        } else {
            this.design.setDesignName(name);
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return design.getDesignName();
    }

    @Override
    public void setDescription(String description) {
        this.description = description;

    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getQuantity() {
        return quantity;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String displayProducts() {
        return "Design Name: " + name + "\n"
                + "Description: " + description + "\n"
                + "Quantity: " + quantity + "\n"
                + "Category: " + category;
    }

    @Override
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String getPrice() {
        return price;
    }


}
