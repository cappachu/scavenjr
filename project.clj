(defproject scavenjr "0.1.0-SNAPSHOT"
  :description "Scavenjr: visual web crawler"
  :url "https://github.com/cappachu/scavenjr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-webdriver/clj-webdriver "0.7.1"]
                 [org.seleniumhq.selenium/selenium-server "2.46.0"]
                 [org.seleniumhq.selenium/selenium-java "2.46.0"]
                 [org.seleniumhq.selenium/selenium-api "2.46.0"]
                 [org.seleniumhq.selenium/selenium-remote-driver "2.46.0"]]

  :main ^:skip-aot scavenjr.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
