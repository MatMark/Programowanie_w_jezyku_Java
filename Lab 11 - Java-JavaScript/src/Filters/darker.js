var effect = function(image) {
  for(var i=0; i < image.getWidth(); i++)
  {
    for(var j=0; j < image.getHeight(); j++)
    {
      var color = new java.awt.Color(image.getRGB(i, j));
      var R = color.getRed()
      var G = color.getGreen()
      var B = color.getBlue()

      if( R - 25 < 0) R = 0
      else R = R - 25

      if( G - 25 < 0) G = 0
      else G = G - 25

      if( B - 25 < 0) B = 0
      else B = B - 25

      var newColor = new java.awt.Color(R.intValue(), G.intValue(), B.intValue());
      image.setRGB(i, j, newColor.getRGB());
    }
  }
  return image;
};