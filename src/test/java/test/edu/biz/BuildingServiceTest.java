package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.teachingres.entity.Building;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.RoomType;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.core.exception.NotFoundException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("buildingService.data.xml")
public class BuildingServiceTest extends BaseServiceTest {
	@Autowired
	private BuildingService buildingService;
	
	@Test
	@ExpectedDatabase(value = "buildingService.createBuilding.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateBuilding() {
		Building building = new Building();
		building.setCode("xz");
		building.setName("行政楼");
		buildingService.createBuilding(building);
	}
	
	@Test
	@ExpectedDatabase(value = "buildingService.createBuildingRoom.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateBuildingRoom() {
		BuildingRoom buildingRoom = new BuildingRoom();
		Building building = new Building();
		building.setId(1L);
		buildingRoom.setBuilding(building);
		buildingRoom.setName("1002");
		buildingRoom.setRoomType(RoomType.normal);
		buildingRoom.setSeatNum(100);
		buildingRoom.setExamSeatNum(50);
		buildingService.createBuildingRoom(buildingRoom);
	}
	
	@Test
	@ExpectedDatabase(value = "buildingService.updateBuilding.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateBuilding() {
		Building building = new Building();
		building.setName("更改建筑名");
		building.setCode("gggggg");
		building.setId(2L);
		buildingService.updateBuilding(building);
	}
	
	@Test
	@ExpectedDatabase(value = "buildingService.updateBuildingRoom.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateBuildingRoom() {
		BuildingRoom buildingRoom = new BuildingRoom();
		buildingRoom.setName("10010");
		buildingRoom.setRoomType(RoomType.practice);
		buildingRoom.setSeatNum(80);
		buildingRoom.setExamSeatNum(40);
		buildingRoom.setId(1L);
		buildingService.updateBuildingRoom(buildingRoom);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的建筑()
	{
		Building building = new Building();
		building.setName("test002");
		building.setCode("test");
		building.setId(1000L);
		buildingService.updateBuilding(building);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的教室()
	{
		BuildingRoom buildingRoom = new BuildingRoom();
		buildingRoom.setName("test002");
		buildingRoom.setId(1000L);
		buildingService.updateBuildingRoom(buildingRoom);
	}
	
	@Test
	@ExpectedDatabase(value = "buildingService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteBuilding() {
		Assert.assertTrue(buildingService.deleteBuilding(2L));
	}
	
	@Test
	@ExpectedDatabase(value = "buildingService.deleteRoom.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteBuildingRoom() {
		Assert.assertTrue(buildingService.deleteBuildingRoom(2L));
	}
	
	@Test
	public void testGetBuilding() {
		Building building = buildingService.getBuilding(1L);
		Assert.assertEquals("春晖楼", building.getName());
		building = buildingService.getBuilding(2L);
		Assert.assertEquals("工字楼", building.getName());
	}
	
	@Test
	public void testGetBuildingRoom() {
		BuildingRoom buildingRoom = buildingService.getBuildingRoom(1L);
		Assert.assertEquals("1001", buildingRoom.getName());
		buildingRoom = buildingService.getBuildingRoom(2L);
		Assert.assertEquals("1001", buildingRoom.getName());
	}
	
	@Test
	public void testCountBuildingRoom() {
		Long count = buildingService.countBuildingRoom(null);
		Assert.assertEquals("2", count.toString());;
	}
}
