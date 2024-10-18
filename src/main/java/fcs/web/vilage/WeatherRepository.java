package fcs.web.vilage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Region, Long> {

  @Query(value = "SELECT DISTINCT region_parent FROM region ORDER BY region_parent ASC", nativeQuery = true)
  List<String> findRegionParents();

  @Query(value = "SELECT DISTINCT region_child FROM region WHERE region_parent = :regionParent AND region_child IS NOT NULL AND region_child != '' ORDER BY region_child ASC", nativeQuery = true)
  List<String> findRegionChild(@Param("regionParent") String regionParent);

  @Query(value = "SELECT nx, ny FROM region WHERE region_parent = :regionParent AND region_child = :regionChild LIMIT 1", nativeQuery = true)
  List<Object[]> findRegionNXNY(@Param("regionParent") String regionParent, @Param("regionChild") String regionChild);
}
