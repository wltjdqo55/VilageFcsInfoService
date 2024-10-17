package fcs.web.vilage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Region, Long> {

  @Query(value = "SELECT DISTINCT region_parent FROM region", nativeQuery = true)
  List<String> findRegionParents();

  @Query(value = "SELECT DISTINCT region_child FROM region WHERE region_parent = :regionParent AND region_child IS NOT NULL AND region_child != ''", nativeQuery = true)
  List<String> findRegionChild(@Param("regionParent") String regionParent);

  @Query(value = "SELECT nx, ny FROM region WHERE region_parent = :regionParent AND region_child = :regionChild LIMIT 1", nativeQuery = true)
  List<Object[]> findRegionNXNY(@Param("regionParent") String regionParent, @Param("regionChild") String regionChild);
}
