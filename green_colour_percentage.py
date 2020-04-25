import cv2
import numpy as np
import matplotlib.pyplot as plt
import cv2
import os
import glob

img_dir = "dataset" # Enter Directory of all images 
data_path = os.path.join(img_dir,'*g')
files = glob.glob(data_path)
data = []
for f1 in files:
    img = cv2.imread(f1)
    grid_RGB = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    grid_HSV = cv2.cvtColor(grid_RGB, cv2.COLOR_RGB2HSV) # Converting to HSV
    lower_green = np.array([18,20,20])
    upper_green = np.array([105,255,255])
    mask= cv2.inRange(grid_HSV, lower_green, upper_green)
    green_perc = (mask==255).mean()
    if green_perc>=0.75:
        data.append((f1,green_perc))
print(data)    
