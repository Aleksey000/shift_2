package ru.cft.starterkit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Item {

    private Long id;
    private String title;
    private Date date_to_end;
    private Long price;
    private String description;
    private String status;
//    private Long tenant_id = null;
    private Long owner_id;
    //    private String photo;

    public Item(String title, String description, Long price, Date date_to_end, UUID baz){
        this.title           = title;
        this.description     = description;
        this.price           = price;
        this.date_to_end     = date_to_end;
        this.baz             = baz;
    }

    @JsonIgnore
    private UUID baz;

    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public Date getDateToEnd() { return date_to_end;}
    public void setDateToEnd(Date date_to_end) { this.date_to_end = date_to_end;}

    public Long getPrice() { return price;}
    public void setPrice(Long price) { this.price = price;}

    public String getDescription() { return description;}
    public void setDescription(String description) { this.description = description;}

    public String getStatus() { return status;}
    public void setStatus(String status) { this.status = status;}

//    public Long getTenantId() { return tenant_id;}
//    public void setTenantId(Long tenant_id) { this.tenant_id = tenant_id;}

    public Long getOwnerId() { return owner_id;}
    public void setOwnerId(Long owner_id) { this.owner_id = owner_id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item entity = (Item) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(title, entity.title) &&
                Objects.equals(date_to_end, entity.date_to_end) &&
                Objects.equals(price, entity.price) &&
                Objects.equals(description, entity.description) &&
                Objects.equals(status, entity.status) &&
//                Objects.equals(tenant_id, entity.tenant_id) &&
                Objects.equals(owner_id, entity.owner_id) &&
                Objects.equals(baz, entity.baz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date_to_end, price, description, status, /*tenant_id,*/ owner_id);
    }

    @Override
    public String toString() {
        return "SampleEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date_to_end='" + date_to_end + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
//                ", tenant_id='" + tenant_id + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", baz=" + baz +
                '}';
    }
}
