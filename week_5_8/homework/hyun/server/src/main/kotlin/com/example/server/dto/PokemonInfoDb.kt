package com.example.server.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "pokemon_favorite")
// JPA의 Entity 클래스는 data class를 사용하지 않는것이 좋다.
// ㄴ lazy 로딩 불가능 (final이 아니어야 함. allOpen으로 해결 가능)
//      => Spring initializer로 생성 시 kotlin-spring 플러그인 디폴트 추가 => 여기서 allOpen을 포함하고 있음 => jpa 추가시 블록 자동 추가
// ㄴ data 클래스의 equals() 같이 자동 생성되는 메서드는 주 생성자에 정의된 프로퍼티들만 구현에 포함된다. => 그래서 모든걸 주생성자에서 하려고 하는데
//      => 이렇게 되면 초기값 설정이 불가능하다 (주 생성자에 없어야 강제 초기값이 가능하기 때문에) => 그래서 메서드 오버라이딩 => data class 장점 X
// ㄴ 자동 구현되는 메서드들이 lazy 로딩에 의해 의도치 않게 동작됨. => stack overflow 발생 가능
// ㄴ 자세히 정리된 블로그 : https://wslog.dev/kotlin-jpa-entity
class PokemonInfoDb (
    @Id
    val number: Int,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var frontImg: String,

    @Column(nullable = false)
    var backImg: String,
) {
    companion object {
        fun valueOf(info: PokemonInfo): PokemonInfoDb {
            return PokemonInfoDb(
                info.number,
                info.name,
                info.frontImg,
                info.backImg
            )
        }
    }
}
