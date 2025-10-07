import numpy as np
import cv2
import os
from common import load_yolo_rectangles

class RectangleTool:
    def __init__(self):
        self.rectangles = []

    def add_rectangle(self, rect_descriptor):
        self.rectangles.append(rect_descriptor)

    def draw(self, image):
        img_copy = image.copy()
        for rect in self.rectangles:
            p1 = rect['p1']
            p2 = rect['p2']
            color = rect.get('color', (0, 255, 0))
            width = rect.get('width', 2)
            label = rect.get('label', None)
            cv2.rectangle(img_copy, p1, p2, color, width)
            if label is not None:
                cv2.putText(img_copy, label, (p1[0], p1[1]-10),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.9, color, 2)
        return img_copy

    def transform_and_store(self, image, output_dir = "out"):
        os.makedirs(output_dir, exist_ok=True)

        transformations = {
            "blur": lambda img: cv2.blur(img, (20, 20)),
            "resize": lambda img: cv2.resize(img, (100, 100)),
            "flipv": lambda img: cv2.flip(img, 0),
            "dilate": lambda img: cv2.dilate(img, np.ones((4, 4), np.uint8))
        }

        for i, rect in enumerate(self.rectangles):
            dirname = f"{output_dir}/class_{i}"
            os.makedirs(dirname, exist_ok=True)

            x1, y1 = rect['p1']
            x2, y2 = rect['p2']
            crop = image[y1:y2, x1:x2]
            cv2.imwrite(dirname + "/orig.png", crop)

            for tf_name, tf_func in transformations.items():
                filename = dirname + f"/tf_{tf_name}.png"
                cv2.imwrite(filename, tf_func(crop))
                print(f"[INFO] Збережено: {filename}")

img = cv2.imread("area.png")
h, w = img.shape[:2]
yolo_rects = load_yolo_rectangles("area.yolo.txt", w, h)

CLASS_COLORS = [
    (255, 0, 0),
    (0, 255, 0),
    (0, 0, 255),
    (255, 255, 0),
    (0, 255, 255),
    (255, 0, 255)
]

rt = RectangleTool()
for rect in yolo_rects:
    rect['color'] = CLASS_COLORS[rect['class']]
    rt.add_rectangle(rect)
rt.transform_and_store(img)
