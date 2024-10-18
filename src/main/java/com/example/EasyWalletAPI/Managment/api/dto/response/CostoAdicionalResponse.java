package com.example.EasyWalletAPI.Managment.api.dto.response;

        import com.example.EasyWalletAPI.Managment.domain.model.entity.Tiempo;
        import lombok.Data;

        import java.math.BigDecimal;
@Data
public class CostoAdicionalResponse {
    private Long id;
    private String descripcion;
    private BigDecimal monto;
    private Tiempo tiempo;

    public CostoAdicionalResponse(Long id, String descripcion, BigDecimal monto, Tiempo tiempo) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.tiempo = tiempo;
    }
}
