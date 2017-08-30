package es.udc.fic.sgsi_magerit.Model.Analisis;

import java.util.List;

import es.udc.fic.sgsi_magerit.Model.Asset.Asset;
import es.udc.fic.sgsi_magerit.Model.Safeguard.Safeguard;
import es.udc.fic.sgsi_magerit.Model.Threat.Threat;

/**
 * Created by err0r on 29/08/2017.
 */
public class AnalisisInfoDTO {
    AssetAnalisisDTO asset;
    List<ThreatAnalisisDTO> threats;
    List<SafeguardAnalisisDTO> safeguards;

    public AnalisisInfoDTO(AssetAnalisisDTO asset, List<ThreatAnalisisDTO> threats,
                           List<SafeguardAnalisisDTO> safeguards) {
        this.asset = asset;
        this.threats = threats;
        this.safeguards = safeguards;
    }

    public AssetAnalisisDTO getAsset() {
        return asset;
    }

    public void setAsset(AssetAnalisisDTO asset) {
        this.asset = asset;
    }

    public List<ThreatAnalisisDTO> getThreats() {
        return threats;
    }

    public void setThreats(List<ThreatAnalisisDTO> threats) {
        this.threats = threats;
    }

    public List<SafeguardAnalisisDTO> getSafeguards() {
        return safeguards;
    }

    public void setSafeguards(List<SafeguardAnalisisDTO> safeguards) {
        this.safeguards = safeguards;
    }
}
