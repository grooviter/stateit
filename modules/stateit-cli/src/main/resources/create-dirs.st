directory("create-temporal-dir-1") {
  path = "/tmp/randomdirectory-3"
}

directory("create-temporal-dir-2") {
  path = "/tmp/randomdirectory-2"
}

directory("create-temporal-dir-3") {
  path = "/tmp/randomdirectory-3"
}

state {
  provider = fileState("/tmp/mystate.json")
}