package com.rapleaf.hank.storage.cueball;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Fetcher2 implements IFetcher {
  private final IFileOps fileOps;
  private final IFileSelector fileSelector;

  public Fetcher2(IFileOps fileOps, IFileSelector fileSelector) {
    this.fileOps = fileOps;
    this.fileSelector = fileSelector;
  }

  @Override
  public void fetch(int fromVersion, int toVersion) throws IOException {
    List<String> remoteFiles = fileOps.listFiles();

    List<String> relevantFiles = new ArrayList(remoteFiles.size());
    for (String fileName : remoteFiles) {
      if (fileSelector.isRelevantFile(fileName, fromVersion, toVersion)) {
        relevantFiles.add(fileName);
      }
    }

    List<String> filesToCopy = fileSelector.selectFilesToCopy(relevantFiles);

    for (String fileName : filesToCopy) {
      fileOps.copyToLocal(fileName);
    }
  }
}
