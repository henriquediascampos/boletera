package com.henriquediascampos.boletera;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NameNotFoundException;

import lombok.Data;
import net.sf.jasperreports.engine.util.ImageUtil;
import net.sf.jasperreports.renderers.ResourceRenderer;

@Data
public class Param {

    private String title;
    private String descricao;
    private ResourceRenderer icon;
    private Double valorTotal;
    private String referencia;
    private String emissor;
    private Integer totalParcelas;
    private LocalDate dataInicio;

    public Param(Map<String, String> requestParam) throws NameNotFoundException {

        if (!requestParam.containsKey("valor_total")) {
            throw new NameNotFoundException("O parametro valor_total é obrigatório!");
        }
        if (!requestParam.containsKey("referencia")) {
            throw new NameNotFoundException("O parametro referencia é obrigatório!");
        }
        if (!requestParam.containsKey("emissor")) {
            throw new NameNotFoundException("O parametro emissor é obrigatório!");
        }
        if (!requestParam.containsKey("total_parcelas")) {
            throw new NameNotFoundException("O parametro total_parcelas é obrigatório!");
        }

        this.title = requestParam.containsKey("titulo") ? requestParam.get("titulo") : "Carnê da devolução da loucura.";
        this.descricao = requestParam.containsKey("descricao") ? requestParam.get("descricao") : "carnê com finalidade de devolver ou juntar uma grama, pra mim mesmo, (não me julgue viviane vc tbm faz isso...)";


        this.valorTotal = Double.parseDouble(requestParam.get("valor_total") );
        this.referencia = requestParam.get("referencia") ;
        this.emissor = requestParam.get("emissor");
        this.totalParcelas = Integer.parseInt(requestParam.get("total_parcelas"));
        this.dataInicio = requestParam.containsKey("descricao") ? LocalDate.parse(requestParam.get("data_inicio")) : LocalDate.now();

        URL resource = ImageUtil.class.getClassLoader().getResource("static/pig.png");
        this.icon = ResourceRenderer.getInstance(resource.toExternalForm(),  false);
    }

    public HashMap<String, Object> getValues() { 
        final HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("title", title);
        values.put("descricao", descricao);
        values.put("valor_total", valorTotal);
        values.put("referencia", referencia);
        values.put("emissor", emissor);
        values.put("total_parcelas", totalParcelas);
        values.put("data_inicio", dataInicio);
        values.put("background", null);
        values.put("icon", icon);

        return values;
    }
}
