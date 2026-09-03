from PIL import Image, ImageDraw, ImageFont

def font(size, bold=True):
    path = "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf" if bold else "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"
    try:
        return ImageFont.truetype(path, size)
    except Exception:
        return ImageFont.load_default()

WHITE = (255, 255, 255, 255)

def icon_shield(d, cx, cy, s, c):
    d.polygon([(cx, cy-s), (cx+s*0.8, cy-s*0.5), (cx+s*0.8, cy+s*0.3),
               (cx, cy+s), (cx-s*0.8, cy+s*0.3), (cx-s*0.8, cy-s*0.5)], fill=c)

def icon_lock(d, cx, cy, s, c):
    d.arc([cx-s*0.5, cy-s*1.1, cx+s*0.5, cy-s*0.1], 180, 360, fill=c, width=int(s*0.18))
    d.rounded_rectangle([cx-s*0.7, cy-s*0.15, cx+s*0.7, cy+s*0.9], radius=s*0.15, fill=c)

def icon_warning(d, cx, cy, s, c):
    d.polygon([(cx, cy-s), (cx+s*0.95, cy+s*0.8), (cx-s*0.95, cy+s*0.8)], fill=c)
    d.rectangle([cx-s*0.08, cy-s*0.45, cx+s*0.08, cy+s*0.15], fill=WHITE)
    d.ellipse([cx-s*0.09, cy+s*0.32, cx+s*0.09, cy+s*0.5], fill=WHITE)

def icon_bug(d, cx, cy, s, c):
    d.ellipse([cx-s*0.55, cy-s*0.7, cx+s*0.55, cy+s*0.7], fill=c)
    for dx in (-1, 1):
        for k in (-0.5, 0, 0.5):
            d.line([cx+dx*s*0.5, cy+k*s*0.6, cx+dx*s*1.1, cy+k*s*0.9], fill=c, width=int(s*0.1))
    d.ellipse([cx-s*0.15, cy-s*1.05, cx+s*0.15, cy-s*0.75], fill=c)

def icon_mask(d, cx, cy, s, c):
    d.rounded_rectangle([cx-s*0.9, cy-s*0.9, cx+s*0.9, cy+s*0.9], radius=s*0.4, fill=c)
    d.ellipse([cx-s*0.5, cy-s*0.25, cx-s*0.15, cy+s*0.1], fill=WHITE)
    d.ellipse([cx+s*0.15, cy-s*0.25, cx+s*0.5, cy+s*0.1], fill=WHITE)

def icon_wifi(d, cx, cy, s, c):
    for i, r in enumerate([1.0, 0.65, 0.32]):
        bbox = [cx-s*r, cy-s*r+s*0.3, cx+s*r, cy+s*r+s*0.3]
        d.arc(bbox, 200, 340, fill=c, width=int(s*0.14))
    d.ellipse([cx-s*0.1, cy+s*0.55, cx+s*0.1, cy+s*0.75], fill=c)

def icon_phone(d, cx, cy, s, c):
    d.rounded_rectangle([cx-s*0.55, cy-s*1.05, cx+s*0.55, cy+s*1.05], radius=s*0.2, fill=c)
    d.rectangle([cx-s*0.4, cy-s*0.8, cx+s*0.4, cy+s*0.65], fill=WHITE)
    d.ellipse([cx-s*0.08, cy+s*0.82, cx+s*0.08, cy+s*0.98], fill=WHITE)

def icon_chat(d, cx, cy, s, c):
    d.rounded_rectangle([cx-s, cy-s*0.75, cx+s, cy+s*0.55], radius=s*0.35, fill=c)
    d.polygon([(cx-s*0.4, cy+s*0.5), (cx-s*0.1, cy+s*0.5), (cx-s*0.5, cy+s*1.0)], fill=c)

def icon_money(d, cx, cy, s, c):
    d.ellipse([cx-s, cy-s, cx+s, cy+s], fill=c)
    fnt = font(int(s*1.1))
    d.text((cx, cy), "$", font=fnt, fill=WHITE, anchor="mm")

def icon_key(d, cx, cy, s, c):
    d.ellipse([cx-s*1.0, cy-s*0.5, cx-s*0.1, cy+s*0.5], outline=c, width=int(s*0.22))
    d.rectangle([cx-s*0.15, cy-s*0.12, cx+s*0.9, cy+s*0.12], fill=c)
    d.rectangle([cx+s*0.55, cy+s*0.12, cx+s*0.7, cy+s*0.35], fill=c)
    d.rectangle([cx+s*0.78, cy+s*0.12, cx+s*0.93, cy+s*0.3], fill=c)

def icon_checklist(d, cx, cy, s, c):
    d.rounded_rectangle([cx-s*0.75, cy-s, cx+s*0.75, cy+s], radius=s*0.15, fill=c)
    for k in (-0.5, 0, 0.5):
        d.line([cx-s*0.45, cy+k*s, cx-s*0.2, cy+k*s+s*0.2], fill=WHITE, width=int(s*0.12))
        d.line([cx-s*0.2, cy+k*s+s*0.2, cx+s*0.15, cy+k*s-s*0.15], fill=WHITE, width=int(s*0.12))

def icon_network(d, cx, cy, s, c):
    pts = [(cx, cy-s), (cx-s*0.9, cy+s*0.5), (cx+s*0.9, cy+s*0.5)]
    for p in pts:
        d.line([cx, cy, p[0], p[1]], fill=c, width=int(s*0.1))
    d.ellipse([cx-s*0.22, cy-s*0.22, cx+s*0.22, cy+s*0.22], fill=c)
    for p in pts:
        d.ellipse([p[0]-s*0.18, p[1]-s*0.18, p[0]+s*0.18, p[1]+s*0.18], fill=c)

def icon_magnifier(d, cx, cy, s, c):
    d.ellipse([cx-s*0.9, cy-s*0.9, cx+s*0.3, cy+s*0.3], outline=c, width=int(s*0.22))
    d.line([cx+s*0.15, cy+s*0.15, cx+s*0.85, cy+s*0.85], fill=c, width=int(s*0.28))

def icon_medical(d, cx, cy, s, c):
    d.rectangle([cx-s*0.28, cy-s*0.9, cx+s*0.28, cy+s*0.9], fill=c)
    d.rectangle([cx-s*0.9, cy-s*0.28, cx+s*0.9, cy+s*0.28], fill=c)

def icon_shop(d, cx, cy, s, c):
    d.polygon([(cx, cy-s), (cx+s, cy-s*0.2), (cx-s, cy-s*0.2)], fill=c)
    d.rectangle([cx-s*0.75, cy-s*0.2, cx+s*0.75, cy+s*0.9], fill=c)
    d.rectangle([cx-s*0.2, cy+s*0.3, cx+s*0.2, cy+s*0.9], fill=WHITE)

def icon_brain(d, cx, cy, s, c):
    d.ellipse([cx-s, cy-s*0.7, cx+s*0.1, cy+s*0.8], fill=c)
    d.ellipse([cx-s*0.1, cy-s*0.7, cx+s, cy+s*0.8], fill=c)
    for k in (-0.3, 0.1, 0.4):
        d.arc([cx-s*0.7, cy+k*s-s*0.2, cx+s*0.7, cy+k*s+s*0.2], 0, 180, fill=WHITE, width=int(s*0.06))

def icon_database(d, cx, cy, s, c):
    w, h = s*1.1, s*0.5
    d.ellipse([cx-w, cy-s-h*0.5, cx+w, cy-s+h*0.5], fill=c)
    d.rectangle([cx-w, cy-s, cx+w, cy+s], fill=c)
    d.ellipse([cx-w, cy+s-h*0.5, cx+w, cy+s+h*0.5], fill=c)
    d.ellipse([cx-w, cy-s-h*0.5, cx+w, cy-s+h*0.5], outline=c, width=2)

def icon_cloud(d, cx, cy, s, c):
    d.ellipse([cx-s*1.1, cy-s*0.1, cx-s*0.1, cy+s*0.7], fill=c)
    d.ellipse([cx-s*0.3, cy-s*0.6, cx+s*0.6, cy+s*0.5], fill=c)
    d.ellipse([cx+s*0.1, cy-s*0.1, cx+s*1.1, cy+s*0.7], fill=c)
    d.rectangle([cx-s*0.9, cy+s*0.15, cx+s*0.9, cy+s*0.7], fill=c)

def icon_gear(d, cx, cy, s, c):
    import math
    n = 8
    outer, inner = s, s*0.7
    pts = []
    for i in range(n*2):
        r = outer if i % 2 == 0 else inner
        ang = math.pi * i / n
        pts.append((cx + r*math.sin(ang), cy - r*math.cos(ang)))
    d.polygon(pts, fill=c)
    d.ellipse([cx-s*0.4, cy-s*0.4, cx+s*0.4, cy+s*0.4], fill=WHITE)

def icon_briefcase(d, cx, cy, s, c):
    d.rounded_rectangle([cx-s, cy-s*0.5, cx+s, cy+s*0.8], radius=s*0.12, fill=c)
    d.rounded_rectangle([cx-s*0.35, cy-s*0.85, cx+s*0.35, cy-s*0.45], radius=s*0.1, outline=c, width=int(s*0.15))
    d.line([cx-s, cy, cx+s, cy], fill=WHITE, width=int(s*0.08))

def icon_globe(d, cx, cy, s, c):
    d.ellipse([cx-s, cy-s, cx+s, cy+s], fill=c)
    d.ellipse([cx-s*0.4, cy-s, cx+s*0.4, cy+s], outline=WHITE, width=int(s*0.08))
    d.line([cx-s, cy, cx+s, cy], fill=WHITE, width=int(s*0.08))
    d.line([cx-s*0.87, cy-s*0.5, cx+s*0.87, cy-s*0.5], fill=WHITE, width=int(s*0.06))
    d.line([cx-s*0.87, cy+s*0.5, cx+s*0.87, cy+s*0.5], fill=WHITE, width=int(s*0.06))

def icon_code(d, cx, cy, s, c):
    fnt = font(int(s*1.6))
    d.text((cx, cy), "</>", font=fnt, fill=c, anchor="mm")

def icon_puzzle(d, cx, cy, s, c):
    d.rounded_rectangle([cx-s*0.9, cy-s*0.9, cx+s*0.9, cy+s*0.9], radius=s*0.15, fill=c)
    d.ellipse([cx+s*0.5, cy-s*1.15, cx+s*1.1, cy-s*0.55], fill=c)
    d.ellipse([cx-s*0.3, cy-s*0.3, cx+s*0.3, cy+s*0.3], fill=WHITE)

def icon_chart(d, cx, cy, s, c):
    bars = [0.5, 1.0, 0.7, 1.3]
    n = len(bars)
    bw = s*0.35
    total_w = n*bw*1.4
    x0 = cx - total_w/2
    for i, h in enumerate(bars):
        x = x0 + i*bw*1.4
        d.rectangle([x, cy+s-h*s, x+bw, cy+s], fill=c)

def icon_rocket(d, cx, cy, s, c):
    d.polygon([(cx, cy-s*1.1), (cx+s*0.4, cy), (cx-s*0.4, cy)], fill=c)
    d.rectangle([cx-s*0.4, cy, cx+s*0.4, cy+s*0.7], fill=c)
    d.polygon([(cx-s*0.4, cy+s*0.7), (cx-s*0.75, cy+s*1.05), (cx-s*0.4, cy+s*0.95)], fill=c)
    d.polygon([(cx+s*0.4, cy+s*0.7), (cx+s*0.75, cy+s*1.05), (cx+s*0.4, cy+s*0.95)], fill=c)

def icon_book(d, cx, cy, s, c):
    d.polygon([(cx, cy-s*0.7), (cx-s, cy-s*0.4), (cx-s, cy+s*0.9), (cx, cy+s*0.6)], fill=c)
    d.polygon([(cx, cy-s*0.7), (cx+s, cy-s*0.4), (cx+s, cy+s*0.9), (cx, cy+s*0.6)], fill=c)

def icon_compare(d, cx, cy, s, c):
    fnt = font(int(s*1.0))
    d.line([cx, cy-s, cx, cy+s], fill=c, width=int(s*0.12))
    d.text((cx, cy), "VS", font=fnt, fill=c, anchor="mm")

def icon_container(d, cx, cy, s, c):
    for i, dx in enumerate([-0.6, 0.6]):
        d.rounded_rectangle([cx+dx*s-s*0.5, cy-s*0.7, cx+dx*s+s*0.5, cy+s*0.7], radius=s*0.08, fill=c)
        for k in (-0.35, 0, 0.35):
            d.line([cx+dx*s-s*0.5, cy+k*s, cx+dx*s+s*0.5, cy+k*s], fill=WHITE, width=int(s*0.05))

def icon_speed(d, cx, cy, s, c):
    d.arc([cx-s, cy-s, cx+s, cy+s], 180, 360, fill=c, width=int(s*0.18))
    import math
    ang = math.radians(300)
    d.line([cx, cy, cx+s*0.75*math.cos(ang), cy+s*0.75*math.sin(ang)], fill=c, width=int(s*0.12))
    d.ellipse([cx-s*0.1, cy-s*0.1, cx+s*0.1, cy+s*0.1], fill=c)

ICONS = {
    "shield": icon_shield, "lock": icon_lock, "warning": icon_warning, "bug": icon_bug,
    "mask": icon_mask, "wifi": icon_wifi, "phone": icon_phone, "chat": icon_chat,
    "money": icon_money, "key": icon_key, "checklist": icon_checklist, "network": icon_network,
    "magnifier": icon_magnifier, "medical": icon_medical, "shop": icon_shop, "brain": icon_brain,
    "database": icon_database, "cloud": icon_cloud, "gear": icon_gear, "briefcase": icon_briefcase,
    "globe": icon_globe, "code": icon_code, "puzzle": icon_puzzle, "chart": icon_chart,
    "rocket": icon_rocket, "book": icon_book, "compare": icon_compare, "container": icon_container,
    "speed": icon_speed,
}

def render_lesson_card(path, icon_name, title, chips, color):
    W, H = 900, 560
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)

    # header band
    d.rectangle([0, 0, W, 90], fill=color)

    # icon circle
    icon_cy = 230
    d.ellipse([W/2-100, icon_cy-100, W/2+100, icon_cy+100], fill=tuple(min(255, c+30) for c in color))
    icon_fn = ICONS.get(icon_name, icon_gear)
    icon_fn(d, W/2, icon_cy, 55, color)

    # title
    title_font = font(40)
    d.text((W/2, 370), title, font=title_font, fill=(30, 30, 30), anchor="mm")

    # chips
    if chips:
        chip_font = font(22, bold=False)
        # measure and lay out chips centered, wrapping to a second row if needed
        paddings = 24
        gaps = 14
        rows = []
        current_row = []
        current_w = 0
        max_w = W - 80
        chip_boxes = []
        for chip in chips:
            bbox = d.textbbox((0, 0), chip, font=chip_font)
            w = (bbox[2]-bbox[0]) + paddings*2
            chip_boxes.append((chip, w))
        # single row if it fits, else wrap
        total = sum(w for _, w in chip_boxes) + gaps*(len(chip_boxes)-1)
        if total <= max_w:
            rows = [chip_boxes]
        else:
            row = []
            rw = 0
            for cb in chip_boxes:
                if rw + cb[1] + gaps > max_w and row:
                    rows.append(row)
                    row = []
                    rw = 0
                row.append(cb)
                rw += cb[1] + gaps
            if row:
                rows.append(row)

        y = 450
        for row in rows:
            total_w = sum(w for _, w in row) + gaps*(len(row)-1)
            x = (W - total_w) / 2
            for chip, w in row:
                d.rounded_rectangle([x, y, x+w, y+44], radius=22, fill=color)
                d.text((x+w/2, y+22), chip, font=chip_font, fill=WHITE, anchor="mm")
                x += w + gaps
            y += 58

    img.save(path)

# ============ Explanatory diagram templates (beyond the simple icon card) ============

PALETTE_TINTS = lambda c: tuple(min(255, int(v*0.35+255*0.65)) for v in c)

def render_stack(path, title, layers, color):
    """Vertical stack of labeled bands, e.g. OSI layers, IaaS/PaaS/SaaS, full-stack architecture."""
    W = 900
    band_h = 70
    top_margin = 150
    H = top_margin + band_h*len(layers) + 60
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(34), fill=WHITE, anchor="mm")

    band_w = 620
    x0 = (W - band_w) / 2
    n = len(layers)
    for i, label in enumerate(layers):
        y0 = top_margin + i * band_h
        shade = tuple(int(c + (255-c) * (i / max(1, n-1)) * 0.55) for c in color)
        d.rectangle([x0, y0, x0+band_w, y0+band_h-8], fill=shade)
        d.text((x0+band_w/2, y0+(band_h-8)/2), label, font=font(24), fill=(30, 30, 30), anchor="mm")
    img.save(path)


def render_flow(path, title, steps, color):
    """Left-to-right process flow with arrows between 3-5 steps."""
    W = 980
    H = 460
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(32), fill=WHITE, anchor="mm")

    n = len(steps)
    box_w, box_h = 165, 150
    gap = (W - n*box_w) / (n+1)
    y0 = 190
    fnt = font(19, bold=False)
    for i, step in enumerate(steps):
        x0 = gap + i*(box_w+gap)
        shade = tuple(int(c + (255-c)*0.25) for c in color)
        d.rounded_rectangle([x0, y0, x0+box_w, y0+box_h], radius=14, fill=shade, outline=color, width=3)
        # wrap text manually into lines
        words = step.split(" ")
        lines, cur = [], ""
        for w in words:
            test = (cur + " " + w).strip()
            if d.textbbox((0,0), test, font=fnt)[2] > box_w - 20 and cur:
                lines.append(cur); cur = w
            else:
                cur = test
        if cur: lines.append(cur)
        ty = y0 + box_h/2 - (len(lines)*13)
        for line in lines:
            d.text((x0+box_w/2, ty), line, font=fnt, fill=(20,20,20), anchor="mm")
            ty += 26
        d.text((x0+box_w/2, y0-28), str(i+1), font=font(26), fill=color, anchor="mm")
        if i < n-1:
            ax = x0+box_w
            ay = y0+box_h/2
            d.line([ax+6, ay, ax+gap-10, ay], fill=color, width=5)
            d.polygon([(ax+gap-10, ay-9), (ax+gap-10, ay+9), (ax+gap+4, ay)], fill=color)
    img.save(path)


def render_compare(path, title, left_title, left_points, right_title, right_points, color):
    """Two side-by-side panels with header + bullet points each."""
    W, H = 940, 520
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(30), fill=WHITE, anchor="mm")

    panel_w = 400
    gap = 40
    y0 = 130
    panel_h = 340
    left_x = W/2 - gap/2 - panel_w
    right_x = W/2 + gap/2

    shade_l = tuple(int(c + (255-c)*0.75) for c in color)
    shade_r = tuple(int(c*0.85) for c in color)

    for x, head, points, headfill, bodyfill, txtcolor in [
        (left_x, left_title, left_points, color, shade_l, (25,25,25)),
        (right_x, right_title, right_points, tuple(int(c*0.7) for c in color), shade_r, WHITE),
    ]:
        d.rounded_rectangle([x, y0, x+panel_w, y0+60], radius=12, fill=headfill)
        d.text((x+panel_w/2, y0+30), head, font=font(24), fill=WHITE, anchor="mm")
        d.rounded_rectangle([x, y0+66, x+panel_w, y0+panel_h], radius=12, fill=bodyfill)
        py = y0+66+30
        bfnt = font(19, bold=False)
        for pt in points:
            d.ellipse([x+22, py-6, x+34, py+6], fill=txtcolor)
            d.text((x+46, py), pt, font=bfnt, fill=txtcolor, anchor="lm")
            py += 44

    # VS divider
    d.ellipse([W/2-28, y0+panel_h/2-28, W/2+28, y0+panel_h/2+28], fill=(255,255,255), outline=color, width=3)
    d.text((W/2, y0+panel_h/2), "VS", font=font(20), fill=color, anchor="mm")
    img.save(path)


def render_hub(path, title, center, satellites, color):
    """Central concept connected to several satellite items."""
    W, H = 900, 560
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(30), fill=WHITE, anchor="mm")

    import math
    cx, cy = W/2, 340
    n = len(satellites)
    radius = 190
    fnt = font(18, bold=False)
    for i, label in enumerate(satellites):
        ang = -math.pi/2 + 2*math.pi*i/n
        sx, sy = cx + radius*math.cos(ang), cy + radius*math.sin(ang)
        d.line([cx, cy, sx, sy], fill=color, width=4)
        shade = tuple(int(c + (255-c)*0.55) for c in color)
        r = 78
        d.ellipse([sx-r, sy-r, sx+r, sy+r], fill=shade, outline=color, width=3)
        words = label.split(" ")
        lines, cur = [], ""
        for w in words:
            test = (cur + " " + w).strip()
            if d.textbbox((0,0), test, font=fnt)[2] > r*1.6 and cur:
                lines.append(cur); cur = w
            else:
                cur = test
        if cur: lines.append(cur)
        ty = sy - (len(lines)*12)
        for line in lines:
            d.text((sx, ty), line, font=fnt, fill=(20,20,20), anchor="mm")
            ty += 24

    d.ellipse([cx-100, cy-100, cx+100, cy+100], fill=color)
    cfnt = font(22)
    words = center.split(" ")
    lines, cur = [], ""
    for w in words:
        test = (cur + " " + w).strip()
        if d.textbbox((0,0), test, font=cfnt)[2] > 170 and cur:
            lines.append(cur); cur = w
        else:
            cur = test
    if cur: lines.append(cur)
    ty = cy - (len(lines)*14)
    for line in lines:
        d.text((cx, ty), line, font=cfnt, fill=WHITE, anchor="mm")
        ty += 28
    img.save(path)


def render_client_server(path, title, left_label, right_label, request_label, response_label, color):
    """Two boxes (client/server) with labeled request/response arrows between them."""
    W, H = 900, 400
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(30), fill=WHITE, anchor="mm")

    box_w, box_h = 220, 140
    y0 = 160
    lx, rx = 90, W-90-box_w
    shade = tuple(int(c + (255-c)*0.55) for c in color)
    d.rounded_rectangle([lx, y0, lx+box_w, y0+box_h], radius=16, fill=shade, outline=color, width=3)
    d.text((lx+box_w/2, y0+box_h/2), left_label, font=font(22), fill=(20,20,20), anchor="mm")
    d.rounded_rectangle([rx, y0, rx+box_w, y0+box_h], radius=16, fill=color)
    d.text((rx+box_w/2, y0+box_h/2), right_label, font=font(22), fill=WHITE, anchor="mm")

    ay1 = y0+box_h*0.35
    ay2 = y0+box_h*0.7
    fnt = font(17, bold=False)
    ax0, ax1 = lx+box_w+8, rx-8
    d.line([ax0, ay1, ax1-14, ay1], fill=color, width=4)
    d.polygon([(ax1-14, ay1-8), (ax1-14, ay1+8), (ax1, ay1)], fill=color)
    d.text(((ax0+ax1)/2, ay1-22), request_label, font=fnt, fill=(20,20,20), anchor="mm")

    d.line([ax1, ay2, ax0+14, ay2], fill=color, width=4)
    d.polygon([(ax0+14, ay2-8), (ax0+14, ay2+8), (ax0, ay2)], fill=color)
    d.text(((ax0+ax1)/2, ay2+22), response_label, font=fnt, fill=(20,20,20), anchor="mm")
    img.save(path)


def render_icon_row(path, title, items, color):
    """Row of icon+label items — for lists of examples/components (upgrade of plain chips)."""
    W = 900
    n = len(items)
    H = 420
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(30), fill=WHITE, anchor="mm")

    cell_w = W / n
    fnt = font(18, bold=False)
    for i, (icon_name, label) in enumerate(items):
        cx = cell_w*i + cell_w/2
        cy = 230
        shade = tuple(int(c + (255-c)*0.55) for c in color)
        d.ellipse([cx-70, cy-70, cx+70, cy+70], fill=shade)
        fn = ICONS.get(icon_name, icon_gear)
        fn(d, cx, cy, 38, color)
        words = label.split(" ")
        lines, cur = [], ""
        for w in words:
            test = (cur + " " + w).strip()
            if d.textbbox((0,0), test, font=fnt)[2] > cell_w-20 and cur:
                lines.append(cur); cur = w
            else:
                cur = test
        if cur: lines.append(cur)
        ty = cy+95
        for line in lines:
            d.text((cx, ty), line, font=fnt, fill=(30,30,30), anchor="mm")
            ty += 24
    img.save(path)


def render_tree(path, title, root, children, color):
    """Simple root -> children tree diagram."""
    W, H = 900, 460
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(30), fill=WHITE, anchor="mm")

    rx, ry = W/2, 170
    rfnt = font(20)
    words = root.split(" ")
    lines, cur = [], ""
    for w in words:
        test = (cur + " " + w).strip()
        if d.textbbox((0,0), test, font=rfnt)[2] > 340 and cur:
            lines.append(cur); cur = w
        else:
            cur = test
    if cur: lines.append(cur)
    box_w = max(180, max(d.textbbox((0,0), l, font=rfnt)[2] for l in lines) + 40)
    box_h = max(64, len(lines)*26 + 20)
    d.rounded_rectangle([rx-box_w/2, ry-box_h/2, rx+box_w/2, ry+box_h/2], radius=12, fill=color)
    ty = ry - (len(lines)*13)
    for line in lines:
        d.text((rx, ty), line, font=rfnt, fill=WHITE, anchor="mm")
        ty += 26

    n = len(children)
    cy = 340
    xs = [W/(n+1)*(i+1) for i in range(n)]
    fnt = font(17, bold=False)
    shade = tuple(int(c + (255-c)*0.6) for c in color)
    for x, label in zip(xs, children):
        d.line([rx, ry+box_h/2, x, cy-32], fill=color, width=3)
        d.rounded_rectangle([x-85, cy-32, x+85, cy+32], radius=10, fill=shade, outline=color, width=2)
        d.text((x, cy), label, font=fnt, fill=(20,20,20), anchor="mm")
    img.save(path)


def render_bars(path, title, items, color, unit=""):
    """Simple labeled bar chart: items = [(label, value_0_to_1), ...]."""
    W, H = 900, 460
    img = Image.new("RGB", (W, H), (250, 250, 252))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 90], fill=color)
    d.text((W/2, 45), title, font=font(30), fill=WHITE, anchor="mm")

    n = len(items)
    chart_h = 240
    base_y = 380
    bar_w = 90
    gap = (W - n*bar_w) / (n+1)
    fnt = font(18, bold=False)
    for i, (label, val) in enumerate(items):
        x0 = gap + i*(bar_w+gap)
        h = chart_h * val
        shade = tuple(int(c*(0.6+0.4*val)) for c in color)
        d.rounded_rectangle([x0, base_y-h, x0+bar_w, base_y], radius=6, fill=shade)
        words = label.split(" ")
        lines, cur = [], ""
        for w in words:
            test = (cur + " " + w).strip()
            if d.textbbox((0,0), test, font=fnt)[2] > bar_w+20 and cur:
                lines.append(cur); cur = w
            else:
                cur = test
        if cur: lines.append(cur)
        ty = base_y+22
        for line in lines:
            d.text((x0+bar_w/2, ty), line, font=fnt, fill=(30,30,30), anchor="mm")
            ty += 22
    d.line([60, base_y, W-40, base_y], fill=(120,120,120), width=2)
    img.save(path)
