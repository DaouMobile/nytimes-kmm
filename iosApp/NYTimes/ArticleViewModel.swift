import SwiftUI
import shared

final class ArticleViewModel: ObservableObject {
    @Published
    private(set) var articles: [Article] = []
    
    private let repository: TimesRepository = .init()
    
    init() {
        self.repository.searchNews(searchText: "", page: 0) { (articles, error) in
            if let error = error {
                print("error: \(error)")
                return
            }
            guard let articles = articles else {
                fatalError()
            }
            self.articles = articles
        }
    }
}
