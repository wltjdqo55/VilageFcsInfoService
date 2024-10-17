package fcs.web.vilage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="region")
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String regionParent;

  private String regionChild;

  private int nx;

  private int ny;

  public Region(Long id, String regionParent, String regionChild, int nx, int ny) {
    this.id = id;
    this.regionParent = regionParent;
    this.regionChild = regionChild;
    this.nx = nx;
    this.ny = ny;
  }
}
