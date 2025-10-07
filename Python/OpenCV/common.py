def load_yolo_rectangles(file_path, img_w, img_h):
    rectangles = []
    with open(file_path, 'r') as f:
        for line in f:
            parts = line.strip().split()
            label, x_center, y_center, width, height = int(parts[0]), *map(float, parts[1:5])
            x1 = int((x_center - width / 2) * img_w)
            y1 = int((y_center - height / 2) * img_h)
            x2 = int((x_center + width / 2) * img_w)
            y2 = int((y_center + height / 2) * img_h)
            rectangles.append({
                'p1': (x1, y1),
                'p2': (x2, y2),
                'class': label
            })
    return rectangles
