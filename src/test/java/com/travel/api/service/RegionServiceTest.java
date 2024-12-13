package com.travel.api.service;

import com.travel.api.repository.RegionRepositoryImpl;
import com.travel.api.vo.Region_mst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegionServiceTest {
    @Mock
   private RegionRepositoryImpl regionRepository;

   @InjectMocks
   private RegionService regionService;

   @BeforeEach
   public void setUp() {
       MockitoAnnotations.openMocks(this);
   }

   @Test
   public void testGetRegions4() {
       List<String> level4List = Arrays.asList("level4-1", "level4-2");
       when(regionRepository.findAllGroupByLevel4(level4List)).thenReturn(Arrays.asList("level4-1", "level4-2"));

       List<Region_mst> result = regionService.getRegions4(level4List);

       assertEquals(2, result.size());
       assertEquals("level4-1", result.get(0).getLevel4());
       assertEquals("level4-2", result.get(1).getLevel4());
       verify(regionRepository, times(1)).findAllGroupByLevel4(level4List);
   }

   @Test
   public void testGetRegions2() {
       when(regionRepository.findAllGroupByLevel2()).thenReturn(Arrays.asList("level2-1", "level2-2"));

       List<Region_mst> result = regionService.getRegions2();

       assertEquals(2, result.size());
       assertEquals("level2-1", result.get(0).getLevel2());
       assertEquals("level2-2", result.get(1).getLevel2());
       verify(regionRepository, times(1)).findAllGroupByLevel2();
   }

   @Test
   public void testGetRegionCds() {
       List<String> level2List = Arrays.asList("level2-1", "level2-2");
       List<String> level4List = Arrays.asList("level4-1", "level4-2");
       when(regionRepository.findRegionCdsByLevel(level2List, level4List)).thenReturn(Arrays.asList("cd1", "cd2"));

       List<String> result = regionService.getRegionCds(level2List, level4List);

       assertEquals(2, result.size());
       assertEquals("cd1", result.get(0));
       assertEquals("cd2", result.get(1));
       verify(regionRepository, times(1)).findRegionCdsByLevel(level2List, level4List);
   }

   @Test
   public void testGetRegionMstsByRegionCds() {
       List<String> regionCds = Arrays.asList("cd1", "cd2");
       Region_mst region1 = new Region_mst();
       region1.setLevel2("level2-1");
       Region_mst region2 = new Region_mst();
       region2.setLevel2("level2-2");
       when(regionRepository.findByRegionCdIn(regionCds)).thenReturn(Arrays.asList(region1, region2));

       List<Region_mst> result = regionService.getRegionMstsByRegionCds(regionCds);

       assertEquals(2, result.size());
       assertEquals("level2-1", result.get(0).getLevel2());
       assertEquals("level2-2", result.get(1).getLevel2());
       verify(regionRepository, times(1)).findByRegionCdIn(regionCds);
   }
}
