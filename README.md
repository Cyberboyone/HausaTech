# Tech Hausa — Free Tech Courses in Hausa

Free, ad-supported Android app teaching modern tech topics — Cybersecurity,
AI, Cloud Computing, Networking, Programming, Web Development, Data Science,
Mobile Development — each structured as Beginner → Intermediate → Advanced,
with lessons, quizzes, and graphical diagrams. Course and level names are in
English; lesson content and quizzes are in Hausa.

## UX redesign + progress tracking (latest pass)

The app was restructured around a Home tab + Progress tab (bottom nav),
replacing the old "flat list of 8 courses" start screen:

- **Home**: greeting, live search across all 144 lessons, a "Continue
  Learning" card that resumes the last-opened lesson with its course's
  progress bar, a scrollable course-icon row, and a progress-bar card
  per course
- **Progress**: an overall completion ring across all 144 lessons plus
  the same per-course progress cards
- **Progress bars added everywhere they were missing**: the Level list
  (per level) and Lesson list (per level, header bar) now show real
  completion fractions, not just lesson counts
- **New warm coral-on-cream theme** (`Theme.kt`), rounded card shapes
  throughout, and each course's brand color now used consistently for
  its icon, progress bars, and diagrams (`courseColorFor`)
- **"Continue" tracking**: opening any lesson now records it as
  last-accessed, which is what powers Home's Continue card

## Status: All 8 courses complete

**144 lessons, 720 quiz questions, fully working:**
- **Cybersecurity** — 18 lessons. Beginner/Intermediate reuse content
  from the standalone Koyi Tsaro app; Advanced (network security,
  encryption, cloud security, ethical hacking, incident response,
  security for small business) is new.
- **Artificial Intelligence** — 18 lessons: AI literacy (what it is, using
  ChatGPT/Claude, prompting, limitations) plus technical depth (neural
  networks, LLMs, generative AI, fine-tuning/RAG, AI agents, careers).
- **Cloud Computing** — 18 lessons: service models, major providers,
  storage, virtualization/containers, scalability, migration, cost
  management, Kubernetes, serverless, architecture patterns, DevOps/CI-CD,
  cloud databases, careers.
- **Networking** — 18 lessons: what a network is, IP addressing,
  routers/switches/modems, Wi-Fi vs Ethernet, OSI model, TCP/IP, DNS,
  bandwidth/latency, VPNs, subnetting/CIDR, load balancing, HTTP/3 & QUIC,
  SDN, 5G, careers.
- **Programming** — 18 lessons: what programming is, variables, data
  types, conditionals, loops, functions, choosing a first language, arrays,
  OOP, Git, debugging, APIs, algorithms, data structures (trees/graphs),
  design patterns, testing, concurrency, secure coding, careers.
- **Web Development** — 18 lessons: how the web works, HTML/CSS/
  JavaScript, browsers, building a first page, responsive design, frontend
  frameworks, backend, databases, REST APIs, hosting/deployment,
  full-stack architecture, performance, web security (XSS/CSRF), PWAs,
  SSR vs CSR, careers.
- **Data Science** — 18 lessons: what data science is, data types and
  sources, spreadsheets, visualization, statistics, Python/Pandas basics,
  data cleaning, SQL, ML for data science, A/B testing, dashboards, data
  ethics, big data, predictive modeling, deep learning, data engineering,
  business analytics, careers.
- **Mobile Development** — 18 lessons: what mobile dev is, Android vs
  iOS, native vs cross-platform, mobile UI/UX, Android Studio/Xcode,
  building a first app, Flutter/React Native, local storage, APIs/backend,
  testing, publishing to app stores, monetization, push notifications,
  mobile security, performance, CI/CD, wearables/IoT, careers.

This was delivered in batches across several messages rather than one
huge response, specifically to keep quality consistent across all eight
courses rather than thinning out by course 5.

## Adding a course (yours or mine, later)

Drop a new file at `assets/courses/<name>.json` following the same shape
as `cybersecurity.json` (id, title in English, icon key, 3 levels each with
lessons + quizzes), then add the filename to `COURSE_FILES` in
`CourseRepository.kt`. No other code changes needed — the whole UI is
data-driven from these files.

## Architecture

- **Content**: `assets/courses/*.json` — one file per course
- **Model**: `CourseFile → CourseLevel → Lesson → QuizQuestion`, with an
  optional `Diagram` per lesson (kotlinx.serialization)
- **Loading**: `CourseRepository` reads and caches the JSON files, fully
  offline, no network needed
- **Progress**: `ProgressStore` (Jetpack DataStore) — completed lessons,
  best quiz score per lesson, and the most recently opened lesson
  (for "Continue Learning"), all on-device, no sign-in. Every screen
  that shows a list of things (Home's course cards, the Level list, the
  Lesson list, the Progress tab) computes and displays real completion
  percentages from this data — nothing is decorative.
- **Diagrams**: every one of the 144 lessons has a visual actually built
  to explain its concept, not just label it — 9 diagram types, each
  matched to what the lesson teaches:
  - **stack** — layered concepts (OSI's 7 layers, IaaS/PaaS/SaaS, deep
    learning's input/hidden/output layers, full-stack architecture)
  - **flow** — numbered step-by-step processes (how DNS resolves, how
    phishing attacks unfold, CI/CD pipelines, incident response phases)
  - **compare** — two-column side-by-side with real bullet points, not
    single words (Android vs iOS, TCP vs UDP, symmetric vs asymmetric
    encryption, SSR vs CSR, native vs cross-platform, etc.)
  - **hub** — a central concept connected to its components (Router/
    Switch/Modem, an OOP class with its properties/methods, Kubernetes
    orchestrating containers, load balancer distributing to servers)
  - **client-server (cs)** — two boxes with labeled request/response
    arrows (REST APIs, DNS lookups, SQL queries, push notifications)
  - **tree** — root-and-branches (arrays/lists, tree data structures)
  - **bars** — labeled bar comparisons (mean vs median, before/after
    performance optimization, 4G vs 5G speed, A/B test results)
  - **icon row** — labeled icon set for lists of examples/tools/careers
    (AI tools, malware types, career paths, HTML tags)
  - **card** — simple icon + title, reserved for the handful of lessons
    that are pure single-concept definitions
  - 3 lessons additionally use interactive Compose diagrams (tappable
    network-layers stack, tappable neural-network layers) for their
    standout concepts, and 2 (cybersecurity intro/phishing) use
    hand-composed illustrations from the very first pass
  - Generation tooling lives in `tools/`: `icon_lib.py` (the icon set +
    all 9 render functions) and `generate_diagrams_v2.py` (the manifest
    mapping every lesson ID to its template + real content, and the
    batch script that writes both the PNGs and the JSON `diagram`
    fields). Re-running it is safe — deterministic filenames, and it's
    not part of the Android build.
- **UI**: Jetpack Compose + Navigation-Compose. Two top-level tabs behind
  a persistent bottom nav bar —
  - **Gida (Home)** — greeting, a live search across all 144 lessons, a
    "Ci Gaba da Koyo" (Continue Learning) card that jumps straight back
    into whatever lesson was last opened, a horizontally-scrolling
    course-icon row, and a progress-bar card per course
  - **Ci Gaba (Progress)** — an overall completion ring across all 8
    courses, plus the same per-course progress cards as Home

  Tapping a course pushes the Level list (Beginner/Intermediate/
  Advanced, each with its own progress bar) → Lesson list (per-lesson
  completion + best score, plus a level-wide progress header) → Lesson
  reader (with diagram) → Quiz → back to the Lesson list. These four
  screens are pushed full-screen without the bottom nav, the way most
  reading/quiz apps hide chrome while you're inside actual content.
- **Theme**: a warm coral-on-cream palette (`ui/theme/Theme.kt`) shared
  app-wide, with each course keeping its own brand color (`courseColorFor`
  in `CourseIcons.kt`, matching the diagram generator's palette exactly)
  for its icon, progress bars, and diagrams. Dynamic/Material-You color
  is off by default so this branding isn't overridden by the user's
  wallpaper.
- **Ads**: AdMob banner, wired to Google's official *test* unit ID —
  replace before publishing


## Important — have a native Hausa speaker review the content

I wrote all the Hausa lesson and quiz text myself, across all 8 courses.
I'm not a certified Hausa translator, and some of this content is
safety-critical (recognizing scams, protecting financial details) or
technically dense (encryption, neural networks) where precise wording
matters. Every lesson lives in plain text in `assets/courses/*.json` —
that's the one place to review. I also went through and corrected an
orthography inconsistency (missing ɓ/ɗ/ƙ hooked consonants on common
words like ɗaya, ƙarfi, ƙasa, ƙa'ida, ɓoye, kuɗi) — but a native
speaker's judgment still beats mine on the rest of the text.

## Publishing checklist

**Done:**
- ✅ All 8 courses, 144 lessons, 720 quiz questions
- ✅ Every lesson has an explanatory diagram
- ✅ Orthography pass on common hooked-consonant words
- ✅ Branded launcher icon (replace with commissioned artwork if you
  want something more polished, but it's no longer a placeholder)
- ✅ `PRIVACY_POLICY.md` drafted (fill in your name/contact, host it
  publicly, required by Google Play for any app using AdMob)
- ✅ `STORE_LISTING.md` drafted — title, short/long descriptions in
  English and Hausa, screenshot list, category, content rating notes

**Still needs you:**
- ⬜ Native Hausa speaker review of all lesson/quiz text (see above —
  this is the one thing I structurally cannot do myself)
- ⬜ Replace the AdMob test App ID (manifest) and banner unit ID
  (`AdBanner.kt`) with your real AdMob IDs from your own AdMob account
- ⬜ First Gradle sync/build in Android Studio — nothing here has been
  compiled (no Android SDK in the environment this was built in), so
  treat that first build as the moment to catch any typos
- ⬜ Fill in and host `PRIVACY_POLICY.md`, paste the live URL into Play
  Console
- ⬜ Capture the actual screenshots listed in `STORE_LISTING.md` once
  you have a running build
- ⬜ Commission a real 512×512 icon and 1024×500 feature graphic for
  the Play Store listing itself (separate from the in-app launcher icon)
- ⬜ Complete Play Console's content rating questionnaire

## Retired

The standalone "Koyi Tsaro" cybersecurity-only app has been retired —
its content lives on as this app's Cybersecurity course. Tech Hausa is
now the single, actively developed app.

## Bug-fix pass (code review, no build environment available)

Since nothing here has been compiled, I did a manual read-through of
every Kotlin file plus cross-checks against all 8 course JSON files.
Found and fixed:

- **Quiz results could be silently lost.** The score was only saved to
  DataStore when the user tapped "Koma zuwa Darussa" on the results
  screen — leaving via the toolbar back arrow or the system back
  gesture skipped saving entirely. Fixed: the score now saves via
  `LaunchedEffect` the moment the results screen appears, regardless of
  how the user leaves afterward.
- **Finishing a quiz sent you to the wrong screen.** It popped all the
  way back to the Level chooser (Beginner/Intermediate/Advanced)
  instead of the Lesson List, where the just-completed lesson's
  checkmark and score are actually visible and the next lesson is one
  tap away. Fixed by popping exactly two screens (Quiz, then Lesson)
  instead of jumping to a named route.
- **AdMob banners were never released.** Every `AndroidView` hosting an
  `AdView` was recreated on each screen visit but never destroyed on
  the way out, leaking a bit of memory and an ad resource every time
  the user navigated between screens (which is most navigation, since
  every screen has a banner). Fixed with `onRelease = { it.destroy() }`.
- **A missing or corrupt lesson image would crash that lesson's
  screen.** `DiagramView` loaded the PNG with no error handling — one
  bad filename or damaged file and the whole screen would crash instead
  of just not showing an image. Fixed with `runCatching`, so it now
  fails silently (shows no image) rather than crashing.
- **A broken course file would fail without a trace.** If one course's
  JSON ever fails to parse, it just vanishes from the course list with
  nothing logged anywhere, which would be a hard bug to track down.
  Added a `Log.e` on parse failure so a broken file is at least
  diagnosable in Logcat.
- **Rotating the phone mid-quiz reset the quiz to question 1.** No
  screen orientation was set, so a configuration change (rotation)
  recreated the Activity and wiped in-progress quiz state (current
  question, selected answers) since it wasn't saved across that kind of
  recreation. Fixed by locking the app to portrait — appropriate for a
  reading/quiz app, and simpler than making every piece of quiz state
  individually rotation-safe.

**Verified clean** (all passed): every image file referenced in course
JSON actually exists on disk (141/141) with no orphans; every
interactive diagram key matches an implemented composable; every
course's `icon` string matches a case in `CourseIcons.kt`; every quiz
question's `answerIndex` points at a real option (720/720); all 144
lesson IDs are globally unique (matters since progress is keyed by
lesson ID alone, not course+lesson); every course JSON is valid and
every lesson has exactly 5 quiz questions with 4 options each.

**Still worth knowing:** none of this was run on a device or emulator —
this was a static read-through, not a substitute for actually building
and clicking through it. The dependency versions in `build.gradle.kts`
(Compose BOM, Navigation, AdMob, etc.) were current as of when this was
written but are worth checking against latest before a real build.
### Lesson content and quiz ordering
The course lessons contain expanded Hausa teaching material with explanations, practical examples, application
guidance, key terms, takeaways, and summaries. Quiz answer positions are shuffled at runtime (once per question,
reshuffled on every retry), so the correct answer is not predictable from its position — scoring, selection, and
correct/wrong states all read the same shuffled copy.

