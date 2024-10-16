package com.schedule.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schedule.jpa.controller.schedule.dto.ScheduleReadResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateResponse;
import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.user.Role;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.infra.client.weather.WeatherClient;
import com.schedule.jpa.infra.client.weather.dto.WeatherResponse;
import com.schedule.jpa.infra.repository.ScheduleRepository;
import com.schedule.jpa.infra.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WeatherClient weatherClient;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    @DisplayName("일정 생성 성공")
    void create_Success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final WeatherResponse weatherResponse = new WeatherResponse("10-15", "Sunny And Humid");
        final ScheduleSaveRequest request = new ScheduleSaveRequest("test title", "test content");
        final Schedule schedule = Schedule.of(user, request.title(), weatherResponse.weather(), request.content());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(weatherClient.getWeather(any())).thenReturn(weatherResponse);
        when(scheduleRepository.save(any())).thenReturn(schedule);

        // when
        final ScheduleSaveResponse response = scheduleService.create(request, 1L);

        // then
        assertThat(response.username()).isEqualTo(user.getName());
        assertThat(response.title()).isEqualTo(schedule.getTitle());
        assertThat(response.content()).isEqualTo(schedule.getContent());
    }

    @Test
    @DisplayName("일정 단건 조회 - 일정이 존재하지 않을때")
    void findSchedule_ScheduleNotFound() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.findSchedule(1L)).isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("일정 단건 조회 - 일정이 존재할때")
    void findSchedule_ScheduleFound() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final WeatherResponse weatherResponse = new WeatherResponse("10-15", "Sunny And Humid");
        final ScheduleSaveRequest request = new ScheduleSaveRequest("test title", "test content");
        final Schedule schedule = Schedule.of(user, request.title(), weatherResponse.weather(), request.content());

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        // when
        final ScheduleReadResponse response = scheduleService.findSchedule(1L);

        // then
        assertThat(response.username()).isEqualTo(schedule.getUser().getName());
        assertThat(response.title()).isEqualTo(schedule.getTitle());
        assertThat(response.content()).isEqualTo(schedule.getContent());
    }

    @Test
    @DisplayName("일정 전체 조회 성공")
    void findSchedules_Success() {
        // given
        final User user1 = User.of("테스트 유저 1", "test1", Role.GENERAL, "test1@gmail.com");
        final WeatherResponse weatherResponse1 = new WeatherResponse("10-15", "Sunny And Humid");
        final Schedule schedule1 = Schedule.of(user1, "첫 번째 일정", weatherResponse1.weather(), "첫 번째 일정 내용");

        final User user2 = User.of("테스트 유저 2", "test2", Role.GENERAL, "test2@gmail.com");
        final WeatherResponse weatherResponse2 = new WeatherResponse("10-15", "Cloudy");
        final Schedule schedule2 = Schedule.of(user2, "두 번째 일정", weatherResponse2.weather(), "두 번째 일정 내용");

        List<Schedule> schedules = List.of(schedule1, schedule2);

        when(scheduleRepository.findAll()).thenReturn(schedules);

        // when
        List<ScheduleResponse> responseList = scheduleService.findSchedules();

        // then
        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0).username()).isEqualTo(schedule1.getUser().getName());
        assertThat(responseList.get(0).title()).isEqualTo(schedule1.getTitle());
        assertThat(responseList.get(0).content()).isEqualTo(schedule1.getContent());

        assertThat(responseList.get(1).username()).isEqualTo(schedule2.getUser().getName());
        assertThat(responseList.get(1).title()).isEqualTo(schedule2.getTitle());
        assertThat(responseList.get(1).content()).isEqualTo(schedule2.getContent());
    }


    @Test
    @DisplayName("일정 수정 - 성공")
    void update_Success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.ADMIN, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "기존 일정 제목", "Sunny", "기존 내용");
        final ScheduleUpdateRequest request = new ScheduleUpdateRequest("업데이트된 제목", "업데이트된 내용");

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        final ScheduleUpdateResponse response = scheduleService.update(request, 1L, 1L);

        // then
        assertThat(response.title()).isEqualTo(request.title());
        assertThat(response.content()).isEqualTo(request.content());
    }

    @Test
    @DisplayName("존재하지 않는 일정 업데이트 시도")
    void update_NonExistSchedule() {
        // given
        final User user = User.of("테스트 유저", "test", Role.ADMIN, "test@gmail.com");
        final ScheduleUpdateRequest request = new ScheduleUpdateRequest("업데이트된 제목", "업데이트된 내용");

        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.update(request, 1L, 1L)).isInstanceOf(
                ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("존재하지 않는 유저 업데이트 시도")
    void update_NonExistUser() {
        // given
        final User user = User.of("테스트 유저", "test", Role.ADMIN, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "기존 일정 제목", "Sunny", "기존 내용");
        final ScheduleUpdateRequest request = new ScheduleUpdateRequest("업데이트된 제목", "업데이트된 내용");

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.update(request, 1L, 1L)).isInstanceOf(
                ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("일정 삭제 - 성공")
    void delete_Success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.ADMIN, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "기존 제목", "Sunny", "기존 내용");

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        scheduleService.delete(1L, 1L);

        // then
        verify(scheduleRepository).deleteById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 일정 삭제")
    void delete_NonExistSchedule() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.delete(1L, 1L))
                .isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 삭제 시도")
    void delete_NonExistUser() {
        // given
        final Schedule schedule = Schedule.of(User.of("기존 유저", "existing", Role.ADMIN, "existing@gmail.com"), "기존 제목",
                "Sunny", "기존 내용");

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.delete(1L, 1L))
                .isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("일정 소유자가 아닌 사용자가 삭제 시도")
    void delete_NotOwner() {
        // given
        final User owner = User.of("소유자", "owner", Role.ADMIN, "owner@gmail.com");
        final User nonOwner = User.of("비소유자", "nonOwner", Role.ADMIN, "nonOwner@gmail.com");
        final Schedule schedule = Schedule.of(owner, "기존 제목", "Sunny", "기존 내용");

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(userRepository.findById(1L)).thenReturn(Optional.of(nonOwner));

        // when & then
        assertThatThrownBy(() -> scheduleService.delete(1L, 1L))
                .isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("비관리자가 삭제 시도")
    void delete_UserNotAdmin() {
        // given
        final User user = User.of("비관리자", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "기존 제목", "Sunny", "기존 내용");

        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when & then
        assertThatThrownBy(() -> scheduleService.delete(1L, 1L))
                .isInstanceOf(ScheduleApplicationException.class);
    }
}
