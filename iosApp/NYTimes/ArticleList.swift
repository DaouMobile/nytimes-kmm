import SwiftUI
import shared

struct ArticleList: View {
    @StateObject
    var viewModel: ArticleViewModel = .init()
    
    var body: some View {
        List(self.viewModel.articles, id: \.hashValue) { (article) in
            ArticleRow(article: article)
        }
    }
}

struct ArticleList_Previews: PreviewProvider {
    static var previews: some View {
        ArticleList()
    }
}
