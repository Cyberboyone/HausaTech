# Diagram generation tools

`icon_lib.py` — a small reusable icon library (drawn with PIL primitives:
shield, lock, cloud, chart, code, etc.) plus 9 render functions, one per
diagram type:

- `render_lesson_card()` — simple icon + title + chips (for pure
  definition lessons)
- `render_stack()` — vertical layered bands (OSI model, IaaS/PaaS/SaaS...)
- `render_flow()` — numbered left-to-right process steps
- `render_compare()` — two-column side-by-side comparison with bullets
- `render_hub()` — central concept + connected satellite items
- `render_client_server()` — two boxes with labeled request/response arrows
- `render_tree()` — root-and-branches diagram
- `render_bars()` — simple labeled bar chart
- `render_icon_row()` — row of icon+label items for lists/examples

`generate_diagrams_v2.py` — the manifest (`M` dict) mapping every lesson
ID to `(template, title, *args)`, plus the batch script that renders each
image and writes a `"diagram"` field into that lesson's entry in the
course JSON. This is the current/authoritative generator — the older
`generate_diagrams.py` (icon+chips only, no longer used) is kept only for
history.

## Adding a diagram for a new lesson

1. Look at the lesson's content and decide which of the 9 templates
   actually explains it (a process → `flow`, two things being contrasted
   → `compare`, a hierarchy → `stack` or `tree`, a request/response →
   `cs`, a list of examples → `iconrow`, etc.)
2. Add an entry to `M` in `generate_diagrams_v2.py`:
   `M["lesson-id"] = ("template", "Title", <template-specific args>)`
   — copy an existing entry using the same template as a starting point.
3. If it's a new course, add its color to `COLORS`.
4. Run `python3 generate_diagrams_v2.py` from this folder. It regenerates
   every lesson currently in the manifest (not just missing ones), so
   re-running after editing an existing entry is the normal way to
   update that one image.
5. New/updated images land in `../app/src/main/assets/images/`.

## Notes

- Needs Pillow (`pip install Pillow`).
- Not part of the Android build — a content tool, not shipped in the APK.
