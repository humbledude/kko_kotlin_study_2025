# 프로젝트 구조
```
com.example.anika
├── config
│   ├── PokeApiProperties.kt
│   └── WebClientConfig.kt
└── pokemon
    ├── adapter
    │   ├── incoming
    │   │   └── web
    │   │       ├── ControllerExceptionHandler.kt
    │   │       ├── GetPokemonResponse.kt
    │   │       ├── PokemonController.kt
    │   │       └── FavoriteController.kt
    │   └── outcoming
    │       ├── web
    │       │   ├── PokeApiAdapter.kt
    │       │   └── PokeApiResponse.kt
    │       └── persistence
    │           ├── FavoriteEntity.kt
    │           ├── FavoriteJpaRepository.kt
    │           └── FavoriteJpaAdapter.kt
    ├── application
    │   ├── port
    │   │   ├── incoming
    │   │   │   ├── PokemonUseCase.kt
    │   │   │   └── FavoriteUseCase.kt
    │   │   └── outcoming
    │   │       ├── PokemonPort.kt
    │   │       └── FavoritePort.kt
    │   └── service
    │       ├── PokemonService.kt
    │       └── FavoriteService.kt
    └── domain
        ├── Pokemon.kt
        └── Stat.kt
```