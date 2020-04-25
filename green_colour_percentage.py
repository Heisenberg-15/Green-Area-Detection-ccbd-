import cv2
import numpy as np
import matplotlib.pyplot as plt


img = cv2.imread('2.jpg')


grid_RGB = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
plt.figure(figsize=(16,6))
plt.imshow(grid_RGB) # Printing the original picture after converting to RGB


grid_HSV = cv2.cvtColor(grid_RGB, cv2.COLOR_RGB2HSV) # Converting to HSV
lower_green = np.array([18,20,20])
upper_green = np.array([125,255,255])

mask= cv2.inRange(grid_HSV, lower_green, upper_green)
res = cv2.bitwise_and(img, img, mask=mask) # Generating image with the green part

print("Green Part of Image")
plt.figure(figsize=(16,6))
plt.imshow(res)
