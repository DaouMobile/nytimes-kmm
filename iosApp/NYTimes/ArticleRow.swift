import SwiftUI
import shared

struct ArticleRow: View {
    let article: Article
    
    var body: some View {
        VStack(alignment: .leading, spacing: 5) {
            Text(self.article.headline?.main ?? "nil")
                .lineLimit(1)
                .font(.headline)
            Text(self.article.pubDate ?? "nil")
                .lineLimit(1)
                .font(.caption)
        }
    }
    
    init(article: Article) {
        self.article = article
    }
}

struct ArticleRow_Previews: PreviewProvider {
    static var previews: some View {
        ArticleRow(article: .init(webUrl: nil, pubDate: nil, headline: nil, multimedia: nil))
    }
}
