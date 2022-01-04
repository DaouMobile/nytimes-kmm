import SwiftUI
import shared

@main
struct NYTimesApp: App {
    var body: some Scene {
        WindowGroup {
            ArticleList()
        }
    }
    
    init() {
        KoinKt.doInitKoin()
    }
}
