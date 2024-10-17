package fcs.web.vilage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WeatherService {

  private final WeatherRepository weatherRepository;

  public List<String> getRegionParentList() {
    return weatherRepository.findRegionParents();
  }

  public List<String> getRegionChildList(String regionParent) {
    return weatherRepository.findRegionChild(regionParent);
  }

  public List<Object[]> getRegionNXNY(String parent, String child) {
    return weatherRepository.findRegionNXNY(parent, child);
  }

}
