package org.briarproject.masterproject.android.blog;

interface OnBlogPostClickListener {

    void onBlogPostClick(BlogPostItem post);

    void onAuthorClick(BlogPostItem post);

    void onLinkClick(String url);
}
