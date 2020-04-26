import cv2
import numpy as np
import matplotlib.pyplot as plt
import cv2
import os
import glob
def find_coordinates(r,c):
    #using haversine distance formula
    #a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
    #c = 2 ⋅ atan2( √a, √(1−a) )
    #d = R ⋅ c
    main_diagonal=74.24 #km
    main_side=(74.24/(2**0.5))*1000 #m
    total_images=72*72
    box_area=(main_side*main_side)/total_images
    box_side=box_area/(2**0.5)
    #given distance and 1 coordinate
    #Formula:	φ2 = asin( sin φ1 ⋅ cos δ + cos φ1 ⋅ sin δ ⋅ cos θ )
    #λ2 = λ1 + atan2( sin θ ⋅ sin δ ⋅ cos φ1, cos δ − sin φ1 ⋅ sin φ2
    n_dec=0.006555
    e_inc=0.00671
    main_n=13.15867
    main_e=77.35686
    top_left=[main_n-((r-1)*n_dec),main_e+((c+1)*e_inc)]
    top_right=[main_n-((r-1)*n_dec),main_e+((c)*e_inc)]
    bottom_left=[main_n-((r)*n_dec),main_e+((c+1)*e_inc)]
    bottom_right=[main_n-((r)*n_dec),main_e+((c)*e_inc)]
    coord=[top_left,top_right,bottom_left,bottom_right]
    return coord
img_dir = "dataset" # Enter Directory of all images 
data_path = os.path.join(img_dir,'*g')
files = glob.glob(data_path)
data = []
for f1 in files:
    t=f1.find("img")
    t+=4
    l=f1.find(".jpg")
    m=f1[t:l]
    [r,c]=m.split("-")
    img = cv2.imread(f1)
    grid_RGB = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    grid_HSV = cv2.cvtColor(grid_RGB, cv2.COLOR_RGB2HSV) # Converting to HSV
    lower_green = np.array([18,20,20])
    upper_green = np.array([105,255,255])
    mask= cv2.inRange(grid_HSV, lower_green, upper_green)
    green_perc = (mask==255).mean()
    if green_perc>=0.75:
        coord=find_coordinates(int(r),int(c))
        data.append((f1,green_perc,coord))
print(data)    
