package org.giglab.ad.core.menu.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.giglab.ad.core.menu.application.dto.command.CreateMenuCommand;
import org.giglab.ad.core.menu.application.port.MenuQueryPort;
import org.giglab.ad.core.menu.application.port.MenuStorePort;
import org.giglab.ad.core.menu.application.usecase.CreateMenuUseCase;
import org.giglab.ad.core.menu.domain.entity.Menu;
import org.giglab.ad.core.menu.domain.exception.MenuDomainException;
import org.giglab.ad.core.menu.domain.exception.MenuErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateMenuUseCase 단위 테스트")
class CreateMenuUseCaseTest {

  @Mock private MenuQueryPort menuQueryPort;
  @Mock private MenuStorePort menuStorePort;

  @InjectMocks private CreateMenuUseCase createMenuUseCase;

  @Test
  @DisplayName("중복되지 않은 URL로 메뉴를 생성하면 저장된 메뉴의 ID를 반환한다")
  void execute_success_returnsMenuId() {
    // given
    CreateMenuCommand command =
        new CreateMenuCommand("대시보드", "/dashboard", 1, List.of("ROLE_ADMINISTRATOR"));
    given(menuQueryPort.findByUrl("/dashboard")).willReturn(Optional.empty());
    Menu savedMenu = Menu.create("대시보드", "/dashboard", 1);
    given(menuStorePort.store(any(Menu.class))).willReturn(savedMenu);

    // when
    Long result = createMenuUseCase.execute(command);

    // then — id는 JPA 영속화 전 null이므로 store 호출 여부로 검증
    verify(menuStorePort).store(any(Menu.class));
    assertThat(result).isEqualTo(savedMenu.getId());
  }

  @Test
  @DisplayName("중복 URL로 메뉴를 생성하면 DUPLICATE_URL 예외가 발생한다")
  void execute_duplicateUrl_throwsMenuDomainException() {
    // given
    CreateMenuCommand command =
        new CreateMenuCommand("대시보드", "/dashboard", 1, List.of("ROLE_ADMINISTRATOR"));
    given(menuQueryPort.findByUrl("/dashboard"))
        .willReturn(Optional.of(Menu.create("기존 메뉴", "/dashboard", 1)));

    // when & then
    assertThatThrownBy(() -> createMenuUseCase.execute(command))
        .isInstanceOf(MenuDomainException.class)
        .hasMessage("이미 존재하는 URL입니다.")
        .satisfies(
            ex ->
                assertThat(((MenuDomainException) ex).getErrorCode())
                    .isEqualTo(MenuErrorCode.DUPLICATE_URL));
  }

  @Test
  @DisplayName("중복 URL이면 store()가 호출되지 않는다")
  void execute_duplicateUrl_doesNotCallStore() {
    // given
    CreateMenuCommand command =
        new CreateMenuCommand("대시보드", "/dashboard", 1, List.of("ROLE_ADMINISTRATOR"));
    given(menuQueryPort.findByUrl("/dashboard"))
        .willReturn(Optional.of(Menu.create("기존 메뉴", "/dashboard", 1)));

    // when
    assertThatThrownBy(() -> createMenuUseCase.execute(command))
        .isInstanceOf(MenuDomainException.class);

    // then
    verify(menuStorePort, never()).store(any(Menu.class));
  }

  @Test
  @DisplayName("메뉴 생성 시 command의 roles가 모두 MenuRole로 매핑되어 store에 전달된다")
  void execute_success_rolesAreMappedToMenu() {
    // given
    List<String> roles = List.of("ROLE_ADMINISTRATOR", "ROLE_PARTNER_ADMIN");
    CreateMenuCommand command = new CreateMenuCommand("광고 관리", "/ads", 2, roles);
    given(menuQueryPort.findByUrl("/ads")).willReturn(Optional.empty());
    Menu savedMenu = Menu.create("광고 관리", "/ads", 2);
    given(menuStorePort.store(any(Menu.class))).willReturn(savedMenu);

    // when
    createMenuUseCase.execute(command);

    // then
    verify(menuStorePort).store(argThat(menu -> menu.getMenuRoles().size() == 2));
  }

  @Test
  @DisplayName("중복 roles가 포함된 command를 실행하면 distinct 처리 후 store에 전달된다")
  void execute_duplicateRoles_storesDistinctRoles() {
    // given
    List<String> rolesWithDuplicate =
        List.of("ROLE_ADMINISTRATOR", "ROLE_ADMINISTRATOR", "ROLE_PARTNER_ADMIN");
    CreateMenuCommand command = new CreateMenuCommand("파트너 관리", "/partners", 3, rolesWithDuplicate);
    given(menuQueryPort.findByUrl("/partners")).willReturn(Optional.empty());
    Menu savedMenu = Menu.create("파트너 관리", "/partners", 3);
    given(menuStorePort.store(any(Menu.class))).willReturn(savedMenu);

    // when
    createMenuUseCase.execute(command);

    // then — 중복 제거 후 2개만 저장되어야 한다
    verify(menuStorePort).store(argThat(menu -> menu.getMenuRoles().size() == 2));
  }
}
