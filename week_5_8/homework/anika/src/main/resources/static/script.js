function applyTheme() {
    const selected = document.getElementById('themeSelector').value;
    const body = document.body;
    body.className = `theme-${selected}`;
    removeEffects();

    if (selected === 'rain') {
        createRainDrops(60);
    } else if (selected === 'snow') {
        createSnowflakes(50);
    }
}

function removeEffects() {
    const effectsDiv = document.getElementById('theme-effects');
    effectsDiv.innerHTML = '';
}

function createRainDrops(count) {
    const effectsDiv = document.getElementById('theme-effects');
    for (let i = 0; i < count; i++) {
        const drop = document.createElement('div');
        drop.className = 'rain-drop';
        drop.style.transform = `translateY(${Math.random() * -200}vh)`;
        drop.style.left = `${Math.random() * 100}vw`;
        drop.style.animationDelay = `${Math.random()}s`;
        drop.style.height = `${10 + Math.random() * 20}px`;
        effectsDiv.appendChild(drop);
    }
}

function createSnowflakes(count) {
    const effectsDiv = document.getElementById('theme-effects');
    for (let i = 0; i < count; i++) {
        const flake = document.createElement('div');
        flake.className = 'snowflake';
        flake.style.transform = `translateY(${Math.random() * -200}vh)`;
        flake.style.left = `${Math.random() * 100}vw`;
        flake.style.animationDuration = `${2 + Math.random() * 5}s`;
        flake.style.opacity = `${0.3 + Math.random() * 0.7}`;
        effectsDiv.appendChild(flake);
    }
}

let currentPokemon = null;
let isCurrentFavorite = false;

async function getPokemon() {
    const id = document.getElementById('pokemonId').value;
    const result = document.getElementById('result');
    const error = document.getElementById('error');
    const favoriteListDiv = document.getElementById('favoriteList');
    result.innerHTML = '';
    error.textContent = '';
    favoriteListDiv.innerHTML = '';

    if (!id) {
        error.textContent = '포켓몬 ID를 입력하세요.';
        return;
    }

    try {
        // 포켓몬 정보 조회
        const res = await fetch(`/pokemon/${id}`);
        if (!res.ok) throw new Error();
        const data = await res.json();
        currentPokemon = data;

        // 즐겨찾기 여부 확인
        isCurrentFavorite = await checkFavoriteStatus(data.id);

        result.innerHTML = renderPokemonCardWithFavorite(data, isCurrentFavorite);
    } catch (e) {
        error.textContent = '포켓몬 정보를 불러오는 데 실패했습니다.';
    }
}

async function checkFavoriteStatus(pokemonId) {
    try {
        const res = await fetch(`/favorites/${pokemonId}/exists`);
        if (!res.ok) return false;
        return await res.json(); // true or false
    } catch {
        return false;
    }
}

// 별(★/☆) 클릭 시
async function toggleFavorite() {
    if (!currentPokemon) return;
    const error = document.getElementById('error');
    error.textContent = '';

    const pokemonId = currentPokemon.id;
    try {
        if (isCurrentFavorite) {
            // 즐겨찾기 삭제
            const res = await fetch(`/favorites/${pokemonId}`, { method: 'DELETE' });
            if (!res.ok) {
                error.textContent = '즐겨찾기 삭제에 실패했습니다.';
                return;
            }
            isCurrentFavorite = false;
        } else {
            // 즐겨찾기 추가
            const res = await fetch(`/favorites/${pokemonId}`, { method: 'POST' });
            if (!res.ok) {
                error.textContent = '즐겨찾기 추가에 실패했습니다.';
                return;
            }
            isCurrentFavorite = true;
        }

        const starBtn = document.getElementById('favoriteStar');
        const starIcon = starBtn.querySelector('i');
        if (isCurrentFavorite) {
            starIcon.className = 'fas fa-star';
            starIcon.style.color = '#ffd600';
            starBtn.title = '즐겨찾기 삭제';
        } else {
            starIcon.className = 'far fa-star';
            starIcon.style.color = '#888';
            starBtn.title = '즐겨찾기 추가';
        }
    } catch (e) {
        error.textContent = isCurrentFavorite
            ? '즐겨찾기 삭제 중 오류가 발생했습니다.'
            : '즐겨찾기 추가 중 오류가 발생했습니다.';
    }
}

// 즐겨찾기 목록 보기
async function getFavoriteList() {
    const error = document.getElementById('error');
    const favoriteListDiv = document.getElementById('favoriteList');
    const result = document.getElementById('result');
    error.textContent = '';
    favoriteListDiv.innerHTML = '';
    result.innerHTML = '';

    try {
        const res = await fetch('/favorites');
        if (!res.ok) throw new Error();
        const favorites = await res.json();
        if (favorites.length === 0) {
            favoriteListDiv.innerHTML = '<p>즐겨찾기한 포켓몬이 없습니다.</p>';
            return;
        }
        favoriteListDiv.innerHTML = '<h2>즐겨찾기 목록</h2>' + favorites.map(renderPokemonCard).join('');
    } catch (e) {
        error.textContent = '즐겨찾기 목록을 불러오는 데 실패했습니다.';
    }
}

// 포켓몬 카드에 별 포함 (즐겨찾기 추가/삭제)
function renderPokemonCardWithFavorite(data, isFavorite) {
    return `
      <div class="card">
        <h2>
          ${data.name} (#${data.id})
          <button 
              id="favoriteStar" 
              onclick="toggleFavorite()" 
              style="font-size:1.6rem; background:none; border:none; cursor:pointer; vertical-align:middle;" 
              title="${isFavorite ? '즐겨찾기 삭제' : '즐겨찾기 추가'}">
              <i class="${isFavorite ? 'fas' : 'far'} fa-star" style="color:${isFavorite ? '#ffd600' : '#888'}"></i>
          </button>
        </h2>
        <p>Height: ${data.height}</p>
        <div class="images">
          <img src="${data.frontImageUrl}" />
          <img src="${data.backImageUrl}" />
        </div>
        <div class="stats">
          <h3>Stats</h3>
          ${data.stats.map(stat => `
            <div>
              <div class="stat-label">${stat.name}</div>
              <div class="stat-bar">
                <span style="width: ${stat.baseStat}px">${stat.baseStat}</span>
              </div>
            </div>
          `).join('')}
        </div>
      </div>
    `;
}

// 즐겨찾기 목록 카드 (별 없음)
function renderPokemonCard(data) {
    return `
      <div class="card">
        <h2>${data.name} (#${data.id})</h2>
        <p>Height: ${data.height ?? ''}</p>
        <div class="images">
          ${data.frontImageUrl ? `<img src="${data.frontImageUrl}" />` : ''}
          ${data.backImageUrl ? `<img src="${data.backImageUrl}" />` : ''}
        </div>
        <div class="stats">
          <h3>Stats</h3>
          ${data.stats ? data.stats.map(stat => `
            <div>
              <div class="stat-label">${stat.name}</div>
              <div class="stat-bar">
                <span style="width: ${stat.baseStat}px">${stat.baseStat}</span>
              </div>
            </div>
          `).join('') : ''}
        </div>
      </div>
    `;
}
