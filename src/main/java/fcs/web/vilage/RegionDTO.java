package fcs.web.vilage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegionDTO {

  private long id;

  private String regionParent;

  private String regionChild;

  private int nx;

  private int ny;

  public RegionDTO(Region region) {
    this.id = region.getId();
    this.regionParent = region.getRegionParent();
    this.regionChild = region.getRegionChild();
    this.nx = region.getNx();
    this.ny = region.getNy();
  }

  public RegionDTO(int nx, int ny) {
    this.nx = nx;
    this.ny = ny;
  }
}
