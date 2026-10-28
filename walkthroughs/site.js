/* Shared navigation for every walkthrough page: the "All problems" left nav and prev / next links.
   Needs problems.js loaded first. */
(function () {
  const P = window.LLD_PROBLEMS || [];
  const here = decodeURIComponent(location.pathname.split('/').pop()).replace(/\.html$/, '');
  const idx = P.findIndex(p => p.slug === here);
  const layout = document.querySelector('.layout');
  const bar = document.querySelector('.topbar');
  if (!layout || !bar) return;

  const css = `
.topbar a.brand{color:inherit;text-decoration:none}
.topbar .crumb a{color:inherit;text-decoration:none}
.topbar a.brand:hover,.topbar .crumb a:hover{color:var(--accent-text)}
.nav-btn{padding:6px 10px}
aside.pnav{position:sticky;top:56px;height:calc(100vh - 56px);overflow:auto;padding:24px 10px 28px 14px;border-right:1px solid var(--border);background:var(--bg-soft)}
.pnav .home{display:flex;align-items:center;gap:6px;font-size:13px;font-weight:600;color:var(--muted);text-decoration:none;padding:6px 10px;border-radius:7px;margin-bottom:6px}
.pnav .home:hover{color:var(--accent-text);background:var(--bg)}
.pnav .lvl{display:block;margin:16px 0 4px 10px;font-size:11.5px;font-weight:700;letter-spacing:.06em;text-transform:uppercase;color:var(--muted);text-decoration:none}
.pnav .lvl:hover{color:var(--accent-text)}
.pnav .item{display:flex;gap:8px;align-items:baseline;padding:5px 10px;border-radius:7px;font-size:14px;color:var(--muted);text-decoration:none;line-height:1.35}
.pnav .item .ic{width:18px;flex:none;text-align:center}
.pnav .item:hover{color:var(--text);background:var(--bg)}
.pnav .item.cur{color:var(--accent-text);background:var(--accent-soft);font-weight:600}
.pnav-backdrop{display:none}
.pn{display:grid;grid-template-columns:1fr 1fr;gap:16px;margin-top:56px;padding-top:24px;border-top:1px solid var(--border)}
.pn a{display:block;border:1px solid var(--border);border-radius:12px;padding:14px 18px;text-decoration:none;color:var(--text);background:var(--bg-card)}
.pn a:hover{border-color:var(--accent)}
.pn small{display:block;color:var(--muted);font-size:12.5px}
.pn b{font-size:16px}
.pn .next{grid-column:2;text-align:right}
@media (min-width:1280px){
  body.has-pnav .layout{grid-template-columns:230px 250px minmax(0,1fr);max-width:1720px}
  body.pnav-hidden .layout{grid-template-columns:250px minmax(0,1fr);max-width:1440px}
  body.pnav-hidden aside.pnav{display:none}
}
@media (max-width:1279px){
  aside.pnav{position:fixed;left:0;top:56px;bottom:0;height:auto;width:280px;z-index:30;transform:translateX(-102%);transition:transform .2s ease;box-shadow:0 10px 30px rgba(0,0,0,.25)}
  body.pnav-open aside.pnav{transform:none}
  body.pnav-open .pnav-backdrop{display:block;position:fixed;inset:56px 0 0 0;background:rgba(0,0,0,.35);z-index:29}
}
@media (max-width:640px){.nav-btn span{display:none}.pn{grid-template-columns:1fr}.pn .next{grid-column:1}}
`;
  const style = document.createElement('style');
  style.textContent = css;
  document.head.appendChild(style);

  // left nav: all problems, grouped by level
  const aside = document.createElement('aside');
  aside.className = 'pnav';
  aside.id = 'pnav';
  aside.setAttribute('aria-label', 'All problems');
  let html = '<a class="home" href="../index.html">← All walkthroughs</a>';
  (window.LLD_LEVELS || []).forEach(level => {
    const list = P.filter(p => p.level === level);
    if (!list.length) return;
    html += `<a class="lvl" href="../index.html?level=${lldLevelSlug(level)}">${level}</a>`;
    html += list.map(p => `<a class="item${p.slug === here ? ' cur' : ''}" href="${p.slug}.html"><span class="ic">${p.icon}</span>${p.title}</a>`).join('');
  });
  aside.innerHTML = html;
  layout.prepend(aside);
  document.body.classList.add('has-pnav');

  const backdrop = document.createElement('div');
  backdrop.className = 'pnav-backdrop';
  document.body.appendChild(backdrop);

  // toggle: collapse the column on wide screens, open a drawer on narrow ones
  const wide = matchMedia('(min-width:1280px)');
  try { if (localStorage.getItem('lld-pnav') === 'hidden') document.body.classList.add('pnav-hidden'); } catch (e) {}
  const btn = document.createElement('button');
  btn.className = 'btn nav-btn';
  btn.type = 'button';
  btn.setAttribute('aria-controls', 'pnav');
  btn.innerHTML = '☰ <span>Problems</span>';
  bar.prepend(btn);
  btn.addEventListener('click', () => {
    if (wide.matches) {
      const hidden = document.body.classList.toggle('pnav-hidden');
      try { localStorage.setItem('lld-pnav', hidden ? 'hidden' : 'shown'); } catch (e) {}
    } else {
      document.body.classList.toggle('pnav-open');
    }
  });
  backdrop.addEventListener('click', () => document.body.classList.remove('pnav-open'));
  document.addEventListener('keydown', e => { if (e.key === 'Escape') document.body.classList.remove('pnav-open'); });

  // keep the current problem in view in a long list
  const cur = aside.querySelector('.cur');
  if (cur) cur.scrollIntoView({block: 'nearest'});

  // prev / next at the end of the page
  const main = document.querySelector('main');
  if (main && idx >= 0) {
    const prev = P[idx - 1], next = P[idx + 1];
    const nav = document.createElement('nav');
    nav.className = 'pn';
    nav.setAttribute('aria-label', 'Previous and next problem');
    nav.innerHTML =
      (prev ? `<a href="${prev.slug}.html"><small>← Previous</small><b>${prev.icon} ${prev.title}</b></a>` : '') +
      (next ? `<a class="next" href="${next.slug}.html"><small>Next →</small><b>${next.icon} ${next.title}</b></a>` : '');
    main.appendChild(nav);
  }
})();
