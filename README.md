# FragileWatermarking
Invisible Digital Image Watermarking in spatial domain using LSB bit manipulation

I have made this project for Digital Image Processing subject in my 4th semester of B.Tech.
It is completely developed in java using eclipse ide. For GUI, I have used Windows Builder plug-in in eclipse.
This project consists of three parts.

1)Watermark Embedding

2)Watermark Extraction

3)Extracted watermark comparison with a manipulated one.

In embedding part, first the image is converted in binary image using local thresholding then it is embedded column wise on cover image.
Each pixel of cover image is splitted into Alpha, Red, Green, Blue part and their corresponding LSB part is changed according to pixel
read from watermark image. In this technique 4 pixels of watermark image can be stored in 1 pixel of cover image.

After embedding an extraction key will be generated that will be used at the time of extraction.

Note: While saving the watermarked image or extracted watermark then you have to manually specify the extension. 
e.g. "watermark.png" i.e. you have to give name+extension

To run the project, use fragilewatermarking.jar, it requires Java Runtime Environment(JRE).

To use the project, import source files available in Watermarking folder in eclispe ide, with Windows Builder plug-in installed.

The excel file conatins results of various image manipulations done on watermarked image 
and then extracting the watermark to compare with the original one.
