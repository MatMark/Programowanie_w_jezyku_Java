var effect = function(image) {
  for(var i=0; i < image.getWidth(); i++)
  {
    for(var j=0; j < image.getHeight(); j++)
    {
      var color = new java.awt.Color(image.getRGB(i, j));
      var newColor = new java.awt.Color(color.getRed(), color.getGreen(), 0);
      image.setRGB(i, j, newColor.getRGB());
    }
  }
  return image;
};