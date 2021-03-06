
projectName()
{
  echo "libHttpSubsMgmt-devel"
}

buildProject()
{
  cd libHttpSubsMgmt && \
  make clean && \
  make

  return $?
}

buildApuImage()
{
  rm -rf apu && \
  mkdir -p apu/data/lib/ && \
  mkdir -p apu/data/include/ && \
  mkdir -p apu/control && \
  HSM_VERSION=`cat libHttpSubsMgmt/Version` && \
  cp libHttpSubsMgmt/include/* apu/data/include/ && \
  cp libHttpSubsMgmt/lib/lib/libHttpSubsMgmt.so \
     apu/data/lib/libHttpSubsMgmt-$HSM_VERSION.so && \
  cp libHttpSubsMgmt/lib/lib/libHttpSubsMgmt.a apu/data/lib/ && \
  echo "cd \$ROOTACT/lib" > apu/control/postinst && \
  echo "ln -f -s libHttpSubsMgmt-$HSM_VERSION.so libHttpSubsMgmt.so" >> apu/control/postinst 
}

projectArchDepend()
{
  # yes, need arch for building HSM
  return 0
}

